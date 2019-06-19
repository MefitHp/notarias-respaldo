package com.palestra.notarias.variables;

import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.io.MapTokenResolver;
import com.palestra.notarias.io.TokenMarkerReplacingReader;
import com.palestra.notarias.pojo.RegisterTableValues;
import com.palestra.notarias.pojo.TableTokens;

public class TokenReplacer {

	static Logger logger = Logger.getLogger(TokenReplacer.class);

	/**
	 * Reemplaza las variables(tokens) del texto html(plantilla) por los valores
	 * correspondientes. Nota: si algun valor esta en null, no se reemplaza el
	 * valor.
	 * 
	 * @param sourceText
	 *            Texto con variables a reemplazar
	 * @param variablesToReplace
	 *            Mapa que contiene: key=nombre variable, value=valor variable.
	 * @param tokenStartMarker
	 *            Ejemplo: '${', '$('
	 * @param tokenEndMarker
	 *            Ejemplo: '}' , ')'
	 * @return Plantilla con los valores correspondientes,
	 */
	public Reader remplazaTokenVariablePorValor(String sourceText,
			Map<String, String> tokens, String tokenStartMarker,
			String tokenEndMarker) {

		MapTokenResolver resolver = new MapTokenResolver(tokens);

		Reader source = new StringReader(sourceText);
		try {
			// Para utilizar cualquier marcador utilizar
			TokenMarkerReplacingReader reader = new TokenMarkerReplacingReader(resolver, source, tokenStartMarker, tokenEndMarker);
			return reader;
		} catch (StringIndexOutOfBoundsException e) {
			logger.error(
					"=====>     No se logro la transformación en el Reader", e);
		}
		return null;
	}

	/**
	 * Dado que el replace de las tablas es por partes, primero se reemplazan
	 * los componentes dentro de la tabla, se agrego un replace interno.
	 * 
	 * @param sourceText
	 * @param listaTablas
	 * @return
	 */
	public Reader reemplazaTableTokens(String sourceText, List<TableTokens> listaTablas) {
		try {
			//Map<String, String> mapaTablas = new HashMap<String, String>();
//			for (TableTokens tabla : listaTablas) {
//				if (tabla.getRegisterValues() == null) {
//					/**
//					 * Si la tabla no tenia registros, se toma la definicion de
//					 * la colunma y la longitud de cada columna se conviente a
//					 * '_'
//					 **/
//					logger.info(String.format("****> tabla: %s no tiene registros.",tabla.getNombreCortoSubForm()));
//					List<FormTokens> columnas = tabla.getColumnDefinicions();
//					StringBuilder columnasTablas = new StringBuilder();
//					for (FormTokens cdef : columnas) {
//						columnasTablas.append(" "
//								+ VariableUtils.replaceNullForChar(cdef.getLongitud()));
//					}
//					mapaTablas.put(tabla.getOriginalName(), columnasTablas.toString());
//					continue;
//				}
//				// Por cada registro de la tabla.
//				for (RegisterTableValues rowValue : tabla.getRegisterValues()) {
//					Map<String, String> mapaFila = new HashMap<String, String>();
//					// Hacer match entre column definition y column component.value
//					for (FormTokens colDefinition : tabla.getColumnDefinicions()) {
//						for (FormTokens componentValue : rowValue.getListaVarSubComponente()) {
//							if (componentValue.getName().equals(colDefinition.getName())) {
//								// Mapa: nombreOriginal, valor
//								mapaFila.put(colDefinition.getOriginalName(), componentValue.getValue());
//							}
//						}
//					}
//					// Ahora el token markers para frm son '${frm:' .. ']}'
//					Reader readerFila = this.remplazaTokenVariablePorValor(tabla.getDefinicionTextoRow(), mapaFila,
//							EnumVariables.TBL_PREFIX.toString(),
//							EnumVariables.TBL_SUFIX.toString());
//					// Reemplazar el texto en la fila-i
//					if (readerFila != null) {
//						rowValue.setValueRegisterText(IOUtils.toString(readerFila));
//						logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//						logger.info(rowValue.getValueRegisterText());
//						logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//					}
//				}
//				String valueToReplace = VariableUtils.daFormatoFilaTabla(tabla.getRegisterValues());
//				mapaTablas.put(tabla.getOriginalName(), valueToReplace);
//			}
//			for (Map.Entry<String, String> entry : mapaTablas.entrySet()) {
//				logger.info(String.format("====> Table token to replace-value: %s-%s", entry.getKey(), entry.getValue()));				
//			}
			
			Map<String, String> mapaTablas = new TreeMap<>();
			for(TableTokens tabla:listaTablas){
				StringBuilder valores = new StringBuilder();
				if(tabla.getNumRegistros()!=null && tabla.getNumRegistros()>-1){					
					for(RegisterTableValues valor:tabla.getRegisterValues()){
						if(valor.getValueRegisterText()!=null && !valor.getValueRegisterText().isEmpty()){
							valores.append(valor.getValueRegisterText());
							//valores.append("<br />");
						} else {
							valores.append("sin dato");
						}
					}					
				}else{
					valores.append("&nbsp;");
				}
				mapaTablas.put(tabla.getOriginalName().substring(0,tabla.getOriginalName().length()-1), valores!=null?valores.toString():null);
			}
			
			//EnumVariables.TBL_PREFIX.toString()+EnumVariables.TBL_IDENTIFIER,
			//EnumVariables.TBL_SUFIX.toString(),
			
			// Reemplazar tablas token por su respecitivo texto.+
			
			Reader readerTexto = this.remplazaTokenVariablePorValor(sourceText,
					mapaTablas, EnumVariables.TBL_PREFIX.toString()+EnumVariables.TBL_IDENTIFIER,
					EnumVariables.TBL_SUFIX.toString());
			return readerTexto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
