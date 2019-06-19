package com.palestra.notarias.escritura;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.FormatoPDFDetalle;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.util.TramiteFileUtils;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.service.OpenOfficeService;
import com.palestra.notarias.utils.ArchivoUtils;

/**
 * 
 * @author sofia
 * 
 */
public class FormatosGenerator {

	static Logger logger = Logger.getLogger(FormatosGenerator.class);

	/**
	 * Genera un archivo .doc a partir del texto en html de un formato previo o
	 * posterior, de un acto dado.
	 * 
	 * @param htmldoc
	 *            Texto en html del formato
	 * @return ruta del archivo recien generado.
	 */
	public String generaDocumentoDeFormato(String idactdoc, String realPath) {
		try {
			ActoDocumentoBo actoDocumentoBo = new ActoDocumentoBoImpl();
			ActoDocumento actDoc = actoDocumentoBo
					.obtenerCompletoPorId(idactdoc);
			if (actDoc == null) {
				logger.info("No se pudo obtener el acto documento.");
				return null;
			}

			String textoFormato = actDoc.getTxtFormato();
			if (textoFormato == null || textoFormato.isEmpty()) {
				logger.info("No existe texto del actoDocumento dado.");
				return null;
			}
			/** obtener ruta donde se colocara el archivo **/
			String tramiteFolder = TramiteFileUtils.obtenRutaFolderByActDoc(idactdoc);
			if (tramiteFolder == null) {
				logger.info("No se pudo crear el folder.");
				return null;
			}

			Date fecha = new Date();
			/**
			 * Ruta archivo:
			 * user.home/uploaded/idtramite/tituloDocumento_miliseconts
			 **/
			String rutaFormato = tramiteFolder + File.separator
					+ actDoc.getDocumento().getDstitulo() + "_"
					+ fecha.getTime();
			/**
			 * Crear el archivo html temporal para posterior convertirlo a .doc
			 **/
			ArchivoUtils.writeStringToFile(textoFormato, rutaFormato + ".html");
			/** Convertir .html to .doc **/
			OpenOfficeService openOfficeHelper = new OpenOfficeService();
			boolean transformOk = openOfficeHelper.convertFile(rutaFormato
					+ ".html", rutaFormato + ".doc");
			/** Eliminar archivo auxiliar .html **/
			boolean b = ArchivoUtils.deleteFile(rutaFormato + ".html");
			if (!b) {
				return null;
			}
			if (!transformOk) {
				/**
				 * si no se transformo el documento retornar null pero despues
				 * de haber eliminado el html
				 **/
				return null;
			}
			return rutaFormato + ".doc";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> extraerPdf(ActoDocumento actoDoc,
			List<String> camposEnString) throws IOException, NotariaException {
		FormularioBo formularioBo = new FormularioBoImpl();
		List<Formulario> formularios = formularioBo
				.buscarFormulariosPorActo(actoDoc.getActo().getIdacto());
		logger.info("=====> identificador de acto: " + actoDoc.getIdactodoc());
		List<PDField> listaCampos = null;
			PDDocument pddDocument = PDDocument.load(actoDoc.getFormatoPdf()
					.getDsruta());
			PDDocumentCatalog pddCatalog = pddDocument.getDocumentCatalog();
			PDAcroForm acroForm = pddCatalog.getAcroForm();
			listaCampos = acroForm.getFields();
		// List<String> =
		camposEnString = new ArrayList<String>();
		List<String> camposFormato = new ArrayList<String>();
		// -------
		// List<PDField> lista = acroForm.getFields();
		// for(PDField field:listaCampos){
		// System.out.printf("Alternate: %s%n",field.getPartialName());
		// camposEnString.add(field.getPartialName());
		// }
		// ---------
		if (!listaCampos.isEmpty()) {
			// camposEnString.addAll(devuelveCamposPdf(listaCampos));
			for (PDField campo : listaCampos) {
				logger.info("\t...Campo en pdf:" + campo.getPartialName());
				camposEnString.add(campo.getPartialName());
			}

			for (FormatoPDFDetalle detalle : actoDoc.getFormatoPdf().getDetalleList()) {
				if((detalle.getSuboperacion()!=null && detalle.getSuboperacion().getIdsuboperacion()!=null) || detalle.getAplicaTodasSuboperaciones()){
					if(detalle.getAplicaTodasSuboperaciones() || (detalle.getSuboperacion().getIdsuboperacion().equals(actoDoc.getActo().getSuboperacion().getIdsuboperacion()))){
						logger.info("\t...Campo en definicion de pdf:" + detalle.getDscampo() + " " + detalle.getDsvariable());
						camposFormato.add(detalle.getDscampo());
					}
				} else {
					logger.info("\t...El Campo no esta asociado a una suboperacion enla definicion de pdf:" + detalle.getDscampo() + " " + detalle.getDsvariable());
				}
			}
			if (camposEnString.size() != camposFormato.size()){				
				throw new NotariaException(String.format("El PDF y nuestros registros no contiene los mismos campos definidos [PDF:%d - campos:%d]%n",camposEnString.size(), camposFormato.size()));
			}
			if (camposEnString.containsAll(camposFormato)) {
				StringBuilder stringVars = new StringBuilder();
				for (FormatoPDFDetalle var : actoDoc.getFormatoPdf().getDetalleList()) {
					stringVars.append(var.getDsvariable()).append(" ");
				}
				FillTemplateWithData dataObj = new FillTemplateWithData();
				logger.info("=====> Cadena variables " + stringVars.length());
				logger.info("=====> Formularios " + formularios.size());
				FiltroVariables filtroVariable = new FiltroVariables();
				filtroVariable.setIdacto(actoDoc.getActo().getIdacto());
				filtroVariable.setIdActoDocumento(actoDoc.getIdactodoc());
				return dataObj.getPairVariableValue(formularios,
						stringVars.toString(), filtroVariable,true);

			} else {
				String mensaje = "Las definiciones de los campos del PDF no concuerdan con los campos definidos en el PDF de la configuración";
				logger.info(mensaje);
				throw new NotariaException(mensaje);
			}
		} else {
			String mensaje = "El PDF no contiene campos, por lo que no puede ser transformado.";
			logger.info(mensaje);
			throw new NotariaException(mensaje);
		}
	}

	/**
	 * Este metodo extrae campos de la configuracion del formato y los compara con el docx, y posteriormente devuelve el mapa de valores
	 * 
	 * @param actoDoc
	 * @return mapa de valores de las variables
	 * @throws IOException
	 * @throws NotariaException
	 */
	public Map<String, String> extraerDocx(ActoDocumento actoDoc)throws IOException, NotariaException {
		List<String> camposFormato = new ArrayList<String>();
		List<String> camposEnString = new ArrayList<String>();
		FormularioBo formularioBo = new FormularioBoImpl();
		List<Formulario> formularios = formularioBo
				.buscarFormulariosPorActo(actoDoc.getActo().getIdacto());
		for (FormatoPDFDetalle detalle : actoDoc.getFormatoPdf().getDetalleList()) {
			if((detalle.getSuboperacion()!=null && detalle.getSuboperacion().getIdsuboperacion()!=null) || detalle.getAplicaTodasSuboperaciones()){
				if(detalle.getAplicaTodasSuboperaciones() || (detalle.getSuboperacion().getIdsuboperacion().equals(actoDoc.getActo().getSuboperacion().getIdsuboperacion()))){
					logger.info("\t...Campo en definicion de pdf:" + detalle.getDscampo() + " " + detalle.getDsvariable());
					camposFormato.add(detalle.getDscampo());
				}
			} else {
				logger.info("\t...El Campo no esta asociado a una suboperacion enla definicion de pdf:" + detalle.getDscampo() + " " + detalle.getDsvariable());
			}
		}
		
		try{
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(actoDoc.getFormatoPdf().getDsruta()));
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						for(String campoFormato:camposFormato){
							if (text != null && text.contains("$"+campoFormato)) {
								camposEnString.add(campoFormato);
							}
						}
					}
				}
			}
		}catch(InvalidFormatException e){
			e.printStackTrace();
			throw new NotariaException("Fallo al abrir docx, error: "+e.getMessage());
		}
		
		if (camposEnString.containsAll(camposFormato)) {
			StringBuilder stringVars = new StringBuilder();
			for (FormatoPDFDetalle var : actoDoc.getFormatoPdf().getDetalleList()) {
				stringVars.append(var.getDsvariable()).append(" ");
			}
			FillTemplateWithData dataObj = new FillTemplateWithData();
			logger.info("=====> Cadena variables " + stringVars.length());
			logger.info("=====> Formularios " + formularios.size());
			FiltroVariables filtroVariable = new FiltroVariables();
			filtroVariable.setIdacto(actoDoc.getActo().getIdacto());
			filtroVariable.setIdActoDocumento(actoDoc.getIdactodoc());
			return dataObj.getPairVariableValue(formularios,
					stringVars.toString(), filtroVariable,true);

		}else {
			String mensaje = "Las definiciones de los campos del docx no concuerdan con los campos definidos la configuración";
			logger.info(mensaje);
			throw new NotariaException(mensaje);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> camposEnPDF(String rutaPDF) throws IOException {
		PDDocument pddDocument = PDDocument.load(rutaPDF);
		PDDocumentCatalog pddCatalog = pddDocument.getDocumentCatalog();
		PDAcroForm acroForm = pddCatalog.getAcroForm();
		List<PDField> listaCampos = acroForm.getFields();
		List<String> camposEnString = new ArrayList<String>();
		for (PDField campo : listaCampos) {
			camposEnString.add(campo.getPartialName());
		}
		return camposEnString;
	}

	// public List<String> devuelveCamposPdf(List<PDField> pdfFields){
	// List<String> campos = new ArrayList<String>();
	// for(PDField campo:pdfFields){
	// System.out.println("nombre de campo en pdf "+campo.getPartialName());
	// campos.add(campo.getPartialName());
	// }
	// return campos;
	// }

	public static void main(String args[]) {
		FormatosGenerator formatosGenerator = new FormatosGenerator();
		String rutaFormato = formatosGenerator.generaDocumentoDeFormato(
				"5dd74ccf2c077266e0b53b6bad7ecb33", "");
		logger.info("====> ruta: " + rutaFormato);

	}
}
