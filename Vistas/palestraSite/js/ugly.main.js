/*! www.ktc.mx ktc-site-mx 04-11-2013 */
function removePreloader() {
	$("#preloader").addClass("hidden-preloader"), $("#sections").css("visibility", "visible"), setTimeout(function() {
		document.getElementById("audio-home").play()
	}, 1e3)
}
var loader = html5Preloader();
/*loader.addFiles("audio_palestra*:media/palestra_sound.mp3||media/plaestra_sound.ogg", "video_home*:media/palestra.webm||media/palestra.mp4", "inteligencia*:media/inteligencia.webm||media/inteligencia.mp4", "video_semantica*:media/semantica.webm||media/semantica.mp4","video_contact*:/media/contact.webm||/media/contact.mp4"), loader.on("finish", function() {
	removePreloader();*/
        loader.addFiles("audio_palestra*:media/palestra_sound.mp3||media/plaestra_sound.ogg", "video_home*:media/palestra.webm||media/palestra.mp4"), loader.on("finish", function() {
	removePreloader();
}), loader.on("error", function(a) {
	console.error(a)
});
var speed_move = 10,
	ready_slider = !1,
	animate_slide2 = !0,
	animate_slide3 = !0,
	animate_slide4 = !0,
	animate_slide5 = !0,
	animate_slide7 = !0,
	slider, is_contact = !1,
	random_execute = !0,
	random_array = [],
	post = null,
	post_view = null,
	colors = ["#eaf0f9", "#6B6556", "#d4def3", "#d4def3", "#e5e8f2", "#00ACA3", "#000000","#575757"],
	resizeWeb = {
		resize_video: function(a, b, c, d) {
			var e = c,
				f = d,
				g = f / e;
			return c = a, d = a * g, b > d && (d = b, c = d / g), {
				width: c,
				height: d
			}
		}
	}, Ktc = {
		Models: {},
		Collections: {},
		Views: {},
		Templates: {}
	};
/*Ktc.Models.Post = Backbone.Model.extend({}), Ktc.Collections.PostCollection = Backbone.Collection.extend({
	model: Ktc.Models.Post,
	url: "data/blog.json",
	comparator: function(a) {
		return a.get("id")
	}
}),*/ Ktc.Models.Team = Backbone.Model.extend({}), Ktc.Collections.TeamCollection = Backbone.Collection.extend({
	model: Ktc.Models.Team,
	url: "data/team.json"
}), Ktc.Templates.teamSwipeTemplate = _.template($("#team-swipe-tmpl").html()), Ktc.Views.TeamSwipeView = Backbone.View.extend({
	el: $("#mouseSwipe"),
	template: Ktc.Templates.teamSwipeTemplate,
	initialize: function() {
		this.collection.bind("reset", this.render, this)
	},
	render: function() {
		return _.each(this.collection.models, function(a) {
			var b = this.template(a.toJSON());
			$(this.el).append(b)
		}, this), $(this.el).swipe({
			TYPE: "panelSwipe",
			HORIZ: !0,
			SNAPDISTANCE: 20,
			DURATION: 750,
			EASING: "swing",
			ARROWS: !1,
			FADEARROWS: !1,
			SLIDESHOWTIME: 4e3,
			AUTOSTART: 0,
			PAUSEONHOVER: !1
		}), this
	}
}), Ktc.Templates.TeamMenuTemplate = _.template($("#team-menu-tmpl").html()), Ktc.Views.TeamMenuView = Backbone.View.extend({
	el: $("#team-swipe-menu"),
	template: Ktc.Templates.TeamMenuTemplate,
	initialize: function() {
		this.collection.bind("reset", this.render, this)
	},
	render: function() {
		return _.each(this.collection.models, function(a) {
			var b = this.template(a.toJSON());
			$(this.el).append(b)
		}, this), $(this.el).find("a").eq(0).addClass("swipe-active"), this
	}
}), Ktc.Templates.PostTemplate = _.template($("#post-tmpl").html()), Ktc.Views.PostView = Backbone.View.extend({
	el: $("#slide6-middle"),
	template: Ktc.Templates.PostTemplate,
	initialize: function() {
		this.collection.bind("reset", this.render, this)
	},
	render: function() {
		var a = this.collection.models.length,
			b = $("#slide6-middle blockquote p");
		if (random_execute) {
			for (var c = 0; a > c; c++) random_array[c] = this.collection.models[c];
			random_array.sort(function() {
				return Math.random() - .5
			}), random_array.push(random_array[random_array.length - 1])
		}
		random_array.pop();
		var d = random_array.length - 1;
		1 === random_array.length && $("#reload-quote").fadeOut(500);
		var e = random_array[d].attributes.text,
			f = random_array[d].attributes.url;
		return b.fadeTo(500, 0), setTimeout(function() {
			b.empty(), b.append(e), b.fadeTo(500, 1), $("#post-twitter").attr("href", "https://twitter.com/intent/tweet?url=" + f + "&via=ktcmex"), $("#post-facebook").attr("href", "https://www.facebook.com/sharer/sharer.php?u=" + f), $("#post-linkedin").attr("href", "http://www.linkedin.com/shareArticle?mini=true&url=" + f)
		}, 500), random_execute = !1, this
	},
	events: {
		"click #reload-quote i": "reloadQuote"
	},
	reloadQuote: function() {
		$("#reload-quote div").addClass("refreshing"), $("#reload-transparent").show(), setTimeout(function() {
			$("#reload-quote div").removeClass("refreshing"), $("#reload-transparent").hide()
		}, 1e3), router.changePost()
	}
}), Ktc.Router = Backbone.Router.extend({
	_data: null,
	_view: null,
	_items: null,
	old_section: 0,
	load_post: !0,
	load_team: !0,
	initialize: function() {
		/*this.load_post && this.showPost(), this.load_post = !1,*/ $(".router-link, #logo-link").click(function(a) {
			a.preventDefault(), speed_move = 1e3;
			var b = $(this).attr("href");
			"contact" == b && (setTimeout(function() {
				$("#slider-navigation").show()
			}, 1e3), slider.goToSlide(6)), Backbone.history.navigate(b, !0)
		})
	},
	routes: {
		"": "home",
		home: "home",
		clients: "clients",
		team: "team",
		contact: "contact"
	},
	home: function() {
		//alert("en casa");
		this.moveSections(0, "home", speed_move), this.old_section = 0, ready_slider && slider.goToSlide(0)
	},
	clients: function() {
		this.moveSections(1, "clients", speed_move), this.old_section = 1
	},
	team: function() {
		if (this.moveSections(2, "team", speed_move), this.old_section = 2, this.load_team) {
			var a = new Ktc.Collections.TeamCollection;
			new Ktc.Views.TeamSwipeView({
				collection: a
			}), new Ktc.Views.TeamMenuView({
				collection: a
			}), a.fetch()
		}
		this.load_team = !1
	},
	contact: function() {
		is_contact = !0, this.moveSections(0, "contact", speed_move), this.old_section = 0, setTimeout(function() {
			$("#slider-navigation").show(), slider.goToSlide(6)
		}, 1e3)
	},
	moveSections: function(a, b, c) {
		if ($("#logo-link").addClass("logo-color"), 0 !== a && ($("#slider-navigation").hide(), $("#logo-link").removeClass("logo-color"), $("#sound-button").removeClass("sound-on"), $("#sound-button").addClass("sound-off"), document.getElementById("audio-home").pause()), a != this.old_section) {
			var d = $("section").eq(a),
				e = $("section").eq(this.old_section),
				f = a < this.old_section ? "-100%" : "100%",
				g = a < this.old_section ? "100%" : "-100%";
			$("section").css({
				zIndex: 1
			}), e.css("z-index", 2), d.css({
				marginLeft: f,
				zIndex: 3,
				visibility: "visible"
			}), e.animate({
				marginLeft: g
			}, c, "easeInOutExpo", function() {
				$(this).css("margin-left", 0), $("section").css("visibility", "hidden"), d.css("visibility", "visible")
			}), d.animate({
				marginLeft: 0
			}, c, "easeInOutExpo")
		}
	},
	/*showPost: function() {
		post = new Ktc.Collections.PostCollection, post_view = new Ktc.Views.PostView({
			collection: post
		}), post.fetch()
	},
	changePost: function() {
		post.fetch()
	}*/
});
var router = new Ktc.Router;
Backbone.history.start({
	root: "/",
	pushState: !0
}), jQuery(document).ready(function(a) {
	function b() {
		var b = Math.round(a(window).height()),
			c = Math.round(a(window).width());
		if (!device.mobile() && !device.tablet()) {
			var d = a("video"),
				e = d.height(),
				f = d.width(),
				g = resizeWeb.resize_video(c, b, f, e);
			d.width(g.width).height(g.height).css("margin-left", (c - g.width) / 2).css("margin-top", (b - g.height) / 2)
		}
		a(".bxslider li, .section-bg").css("height", b)
	}

	function c(b, c, d, e) {

		var f = (new Date).getTime();
		return delta_of_interest = e, 0 !== delta_of_interest ? i + h > f - g ? (b.preventDefault(), void 0) : (0 > delta_of_interest ? a(".home-section").next("li").length && (g = f, slider.goToNextSlide()) : a(".home-section").prev("li").length && (g = f, slider.goToPrevSlide()), void 0) : void 0
	}

	function d(b) {
		//alert("gris") PANTALLA DOS
		a(".journey-item").fadeTo(200, 1), a(".journey-item").eq(b).fadeTo(200, 1, function() {
			return b += 1, d(b), a(".journey-line").eq(b - 1).find("div").css("width", "100%"), 4 == b ? 0 : void 0
		})
	}

	function e() {
		//alert("negrito");
		device.mobile() || device.tablet() || document.getElementById("video-business").play(), animate_slide3 = !1
	}

	function f() {
		device.mobile() || device.tablet() || document.getElementById("video-roll").play(), a("#brand-circle").fadeTo(300, 1), a("#brand-circle-shadow").fadeTo(300, .5), setTimeout(function() {
			a("#brand-story div, #brand-utility div").animate({
				width: "100%"
			}, 300, function() {
				a(this).parent().find("span").fadeTo(300, 1), setTimeout(function() {
					a("#customer-journey").fadeTo(300, 1), animate_slide5 = !1
				}, 300)
			})
		}, 500)
		//alert("blanquito")
	}
	a(document).bind("dragstart", function() {
		return !1
	}), device.mobile() || device.tablet() ? a("video").hide() : setTimeout(function() {
		document.getElementById("video-home").play()
	}, 1e3), loader.filesLoading > 0 && removePreloader();
	var g = 0,
		h = 500,
		i = 1e3;
	a("#home").mousewheel(c), b();
	var j = device.mobile() || device.tablet() ? !0 : !1;
	slider = a(".bxslider").bxSlider({
		mode: "vertical",
		useCSS: j,
		speed: 1e3,
		infiniteLoop: !1,
		easing: "easeInOutExpo",
		preventDefaultSwipeY: !0,
		adaptiveHeight: !0,
		pager: !1,
		controls: !1,
		onSliderLoad: function() {
			ready_slider = !0, a("#slider-navigation").show()
		},
		onSlideBefore: function(b, c, d) {
			if (!device.mobile() && !device.tablet()) {
				var e = a(".home-section").eq(c),
					f = a(".home-section");
				3 == c && animate_slide4 === !1 && (f.css("background-color", "#d4def3"), a("#magic-gradient").hide()), f.css("background-color", e.css("background-color")), f.find(".gradient").hide(), f.addClass("change-bg"), f.css("background-color", colors[d])
			}
			var g = slider.getCurrentSlide();
			
			////////// COMPORTAMIENTO POR SLIDE DENTRO DE PAL_NAV.JS
			comporSlide(g);
		

			setTimeout(function() {

				//// AQUI SE CACHAN LOS EVENTOS AL CAMBIAR SLIDERS DESPUES
				

				0 === g ? (a("#logo-link").addClass("logo-color"), a("#slider-navigation").show(), device.mobile() || device.tablet() || document.getElementById("video-home").play()) : (a("#logo-link").removeClass("logo-color"), a("#slider-navigation").show(), device.mobile() || device.tablet() || document.getElementById("video-home").pause()), a(".slider-navigation-item").removeClass("item-active"), a(".slider-navigation-item").eq(g).addClass("item-active")
			}, 500)
		},
		onSlideAfter: function(b, c, g) {
			a(".home-section").eq(g).find(".gradient").fadeIn(), setTimeout(function() {
				a(".home-section").removeClass("change-bg")

				//// AQUI SE CACHAN LOS EVENTOS AL CAMBIAR SLIDERS ANTES
				//alert("despues")

				


			}, 500), 0 !== g && (document.getElementById("audio-home").pause(), a("#sound-button").removeClass("sound-on"), a("#sound-button").addClass("sound-off")), 1 == g && animate_slide2 === !0 && d(0), 2 == g && animate_slide3 === !0 && e(), 3 == g && animate_slide4 === !0 && setTimeout(function() {
				var b = a("#home-magic"),
					c = (a("#magic"), a("#magic-gradient")),
					d = a(window).innerHeight() / 2,
					e = a(window).width() / 2;


				animate_slide4 = !1, b.mousemove(function(b) {
					c.css({
						"background-image": "-webkit-radial-gradient(" + b.pageX + "px " + b.pageY + "px, 500px 500px, #ecf2fa 0%, #d0dff6 100%)"
					}), c.css({
						"background-image": "-moz-radial-gradient(" + b.pageX + "px " + b.pageY + "px, 500px 500px, #ecf2fa 0%, #d0dff6 100%)"
					}), c.css({
						"background-image": "-ms-radial-gradient(" + b.pageX + "px " + b.pageY + "px, 500px 500px, #ecf2fa 0%, #d0dff6 100%)"
					}), a(".light i").css({
						"text-shadow": i(Math.floor(g(b.pageX, e) / 25)) + "px " + i(Math.floor(h(b.pageY, d) / 25)) + "px " + Math.floor(f(b.pageX, b.pageY, e, d) / 40) + "px #c6d5ea"
					}), a(".light div").css({
						"box-shadow": i(Math.floor(g(b.pageX, e) / 25)) + "px " + i(Math.floor(h(b.pageY, d) / 25)) + "px " + Math.floor(f(b.pageX, b.pageY, e, d) / 40) + "px #c6d5ea"
					})

				});
				var f = function(a, b, c, d) {
					return x = Math.floor(Math.abs(a - c)), y = Math.floor(Math.abs(b - d)), Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))
				}, g = function(a, b) {
						return Math.floor(a - b)
					}, h = function(a, b) {
						return Math.floor(a - b)
					}, i = function(a) {
						return a + -2 * a
					}
			}, 500), 3 == g && animate_slide4 === !1 && a("#magic-gradient").show(), 4 == g && animate_slide5 === !0 && f(), 6 == g && animate_slide7 === !0 && (device.mobile() || device.tablet() || document.getElementById("video-contact").play(), animate_slide7 = !1)
		}
	}), a(window).resize(function() {
		b()
	}), window.addEventListener("orientationchange", function() {
		slider.goToNextSlide()
	}, !1), a("#menu-link").click(function(b) {
		b.preventDefault(), a("#header-menu").fadeIn(), a("#menu-link").fadeOut(250), a("#menu-close-link").fadeIn(250), setTimeout(function() {}, 500), a("#header-menu ul").animate({
			left: "0%"
		}, 500, "easeInOutExpo", function() {})
	}), a("#menu-close-link").click(function(b) {
		b.preventDefault(), setTimeout(function() {
			a("#header-menu").fadeOut(250)
		}, 500), a("#header-menu ul").animate({
			left: "100%"
		}, 500, "easeInOutExpo", function() {
			a("#menu-close-link").hide(), a("#menu-link").fadeIn(250)
		})
	}), a("#sound-button div").click(function() {
		a(this).parent().toggleClass("sound-off sound-on"), a(this).parent().hasClass("sound-off") ? document.getElementById("audio-home").pause() : document.getElementById("audio-home").play()
	}), a("section").delegate(".button", "click", function(a) {
		a.preventDefault(), slider.goToNextSlide()
	}), a(".slider-navigation-item i").hover(function() {
		a(this).parent().addClass("item-hover")
	}, function() {
		a(this).parent().removeClass("item-hover")
	}), a("#slider-navigation").delegate("i", "click", function() {
		slider.goToSlide(a(this).closest(".slider-navigation-item").index())
	}), a("#slide7-middle p").delegate("span", "click", function() {
		a("#touch-overlay").fadeIn();
		var b = a(this).offset().left,
			c = "#" + a(this).attr("data-input"),
			d = a(c).height() / 2,
			e = a(this).offset().top - d + 10;
		a(c).find("input").eq(0).focus(), "#input-contact" == c && (c = a("#input-kind div").eq(0).hasClass("selected") ? "#input-contact2" : "#input-contact"), a(c).hasClass("input") && (e += 15), a(c).hasClass("input-textarea") && (b += 225), a(c).css({
			top: e,
			left: b
		}), setTimeout(function() {
			a(c).addClass("show-input")
		}, 200)
	});
	var k = "micorreo@ejemplo.com",
		l = "1234567890",
		m = ["", "", "", "", "escribe tu correo electrónico", "número telefónico", "tú nombre, quisieramos saber por quién preguntar", "escribenos tus necesidades o comentarios"];
	a(".select").delegate("div", "click", function() {
		var b = a(this).parent().attr("id");
		b = "#span-" + b.split("-")[1], a(this).siblings().removeClass("selected"), a(this).addClass("selected"), a("#touch-overlay").fadeOut(), a(this).parent().removeClass("show-input"), a(b).text(a(this).text().substring(0, a(this).text().length - 1)), "#span-kind" == b && (0 === a(this).index() ? (a("#span-contact").text(l), a("#to").text("al")) : (a("#span-contact").text(k), a("#to").text("a")))
	}), a(".input").delegate("i", "click", function() {
		var b = a(this).parent(),
			c = b.attr("id");
		b.index();
		var d = b.find("p"),
			e = "";
		if (e = b.find("input").val(), b.hasClass("input-mail")) {
			if (e = b.find("input").eq(0).val() + "@" + b.find("input").eq(1).val() + "." + b.find("input").eq(2).val(), "" === b.find("input").eq(0).val() || "" === b.find("input").eq(1).val() || "" === b.find("input").eq(2).val()) return d.text("Por favor introduzca un mail correcto"), 0;
			k = e
		}
		if (b.hasClass("input-phone")) {
			var f = /^\d+$/;
			if (!e.match(f)) return d.text("Solo números."), 0;
			d.text(m[5]), l = e
		}
		return 0 === e.length ? (d.text("El campo no puede ir vacío."), 0) : (d.text(m[b.index()]), c = "#span-" + c.split("-")[1], a("#touch-overlay").fadeOut(), b.removeClass("show-input"), "#span-contact2" == c ? a("#span-contact").text(e) : a(c).text(e), void 0)
	}), a("#send-message").click(function(b) {
		b.preventDefault();
		var c = a("#span-subject").text(),
			d = a("#span-kind").text(),
			e = a("#span-contact").text(),
			f = a("#span-name").text(),
			g = a("#span-comment").text();
		"" !== c && "" !== d && "" !== e && "" !== f && "" !== g && "mail@example.com" != e && "1234567890" != e && "me" != f && a.post("/php/mail.php", {
			subject: c,
			kind: d,
			contact: e,
			name: f,
			comment: g
		}, function(b) {
			"ok" === b.code ? (a("#contact-form").hide(), a("#success-message p").text(b.text), a("#success-message").fadeIn()) : console.log("fail")
		}, "json")
	}), a("#touch-overlay").click(function() {
		a(this).fadeOut(), a(".show-input").removeClass("show-input")
	}), a("#show-form").click(function(b) {
		b.preventDefault(), a("#success-message").hide(), a("#contact-form").fadeIn()
	}), a("#social-icons a").tipsy({
		gravity: "s",
		fade: !0,
		opacity: 1,
		offset: -5
	}), a("#top-link").click(function() {
		slider.goToSlide(0)
	})
});