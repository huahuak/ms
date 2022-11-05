"use strict";

function _toConsumableArray(e) {
	if (Array.isArray(e)) {
		for (var t = 0, n = Array(e.length); t < e.length; t++) n[t] = e[t];
		return n
	}
	return Array.from(e)
}

function initParams() {
	var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
		t = Object.assign({}, {
			id: TIMER.getStamp() + "",
			func: function() {},
			param: "",
			delay: 0,
			interval: 1e3,
			time: null
		}, e);
	return t.delay = parseInt(t.delay), isNaN(t.delay) && (t.delay = 0), t.interval = parseInt(t.interval), (isNaN(t
		.interval) || t.interval < 0) && (t.interval = 1e3), t
}

function delayExec() {
	var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {};
	setTimeout(function() {
		e.func(e.param)
	}, e.delay)
}

function isTime() {
	return /^([0-1]\d|2[0-3]):[0-5]\d:[0-5]\d$/.test(arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] :
		"")
}

function isDate() {
	return /^[1-2]\d{3}-(0?[1-9]||1[0-2])-(0?[1-9]||[1-2][1-9]||3[0-1])$/.test(arguments.length > 0 && void 0 !==
		arguments[0] ? arguments[0] : "")
}

function isNull() {
	return !/\S/.test(arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "")
}

function getDateObject() {
	var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
		t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
		n = e ? new Date(e) : new Date,
		r = n.getFullYear(),
		a = n.getMonth() + 1,
		o = n.getDate(),
		i = n.getHours(),
		u = n.getMinutes(),
		g = n.getSeconds(),
		s = n.getDay();
	return t && (a < 10 && (a = "0" + a), o < 10 && (o = "0" + o), i < 10 && (i = "0" + i), u < 10 && (u = "0" + u), g <
		10 && (g = "0" + g)), {
		year: r,
		month: a,
		date: o,
		hours: i,
		minutes: u,
		seconds: g,
		day: s
	}
}
Object.defineProperty(exports, "__esModule", {
	value: !0
});
var _slicedToArray = function() {
		function e(e, t) {
			var n = [],
				r = !0,
				a = !1,
				o = void 0;
			try {
				for (var i, u = e[Symbol.iterator](); !(r = (i = u.next()).done) && (n.push(i.value), !t || n.length !==
						t); r = !0);
			} catch (e) {
				a = !0, o = e
			} finally {
				try {
					!r && u.return && u.return()
				} finally {
					if (a) throw o
				}
			}
			return n
		}
		return function(t, n) {
			if (Array.isArray(t)) return t;
			if (Symbol.iterator in Object(t)) return e(t, n);
			throw new TypeError("Invalid attempt to destructure non-iterable instance")
		}
	}(),
	timers = [],
	countdownObjects = {},
	oneDaySeconds = 864e5,
	TIMER = {
		getLatestDates: function() {
			for (var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 7, t = arguments.length >
					1 && void 0 !== arguments[1] && arguments[1], n = !(arguments.length > 2 && void 0 !==
						arguments[2]) || arguments[2], r = arguments.length > 3 && void 0 !== arguments[3] ?
					arguments[3] : "-", a = TIMER.getStamp(), o = [], i = 1; i <= e; i++) {
				var u = getDateObject(a - (e - i) * oneDaySeconds, n),
					g = u.year,
					s = u.month,
					c = u.date,
					l = "text" == r ? g + "年" + s + "月" + c + "日" : "" + g + r + s + r + c;
				o.push(l)
			}
			return t ? o : [o[0], o[o.length - 1]]
		},
		getNextDates: function() {
			for (var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 7, t = arguments.length >
					1 && void 0 !== arguments[1] && arguments[1], n = !(arguments.length > 2 && void 0 !==
						arguments[2]) || arguments[2], r = arguments.length > 3 && void 0 !== arguments[3] ?
					arguments[3] : "-", a = TIMER.getStamp(), o = [], i = 1; i <= e; i++) {
				var u = getDateObject(a + i * oneDaySeconds, n),
					g = u.year,
					s = u.month,
					c = u.date,
					l = "text" == r ? g + "年" + s + "月" + c + "日" : "" + g + r + s + r + c;
				o.push(l)
			}
			return t ? o : [o[0], o[o.length - 1]]
		},
		getNextWeekFirstDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getNextWeekDates(!1, e, t)[0]
		},
		getLastWeekFirstDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getLastWeekDates(!1, e, t)[0]
		},
		getThisWeekFirstDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getThisWeekDates(!1, e, t)[0]
		},
		getRangeWeekFirstDate: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-";
			return TIMER.getRangeWeekDates(e, !1, t, n)[0]
		},
		getNextWeekLastDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getNextWeekDates(!1, e, t)[1]
		},
		getLastWeekLastDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getLastWeekDates(!1, e, t)[1]
		},
		getThisWeekLastDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getThisWeekDates(!1, e, t)[1]
		},
		getRangeWeekLastDate: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-";
			return TIMER.getRangeWeekDates(e, !1, t, n)[1]
		},
		getNextMonthLastDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getNextMonthDates(!1, e, t)[1]
		},
		getLastMonthLastDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getLastMonthDates(!1, e, t)[1]
		},
		getThisMonthLastDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getThisMonthDates(!1, e, t)[1]
		},
		getYearMonthLastDate: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
				n = !(arguments.length > 2 && void 0 !== arguments[2]) || arguments[2],
				r = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : "-";
			return 0 == e && (e = (new Date).getFullYear()), 0 == t && (t = (new Date).getMonth() + 1), TIMER
				.getYearMonthDates(e, t, !1, n, r)[1]
		},
		getYearDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-";
			if (e = parseInt(e), isNaN(e)) return console.error("year参数错误");
			if (e < 0 || e > 9999) return console.error("year参数错误");
			0 == e && (e = (new Date).getFullYear());
			for (var r = [], a = 1; a <= 12; a++) r.push(TIMER.getYearMonthDates(e, a, !0, t, n));
			return r
		},
		getYearMonthDates: function(e, t) {
			var n = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
				r = !(arguments.length > 3 && void 0 !== arguments[3]) || arguments[3],
				a = arguments.length > 4 && void 0 !== arguments[4] ? arguments[4] : "-";
			if (e = parseInt(e), t = parseInt(t), isNaN(e)) return console.error("year参数错误");
			if (isNaN(t)) return console.error("month参数错误");
			if (e <= 0 || e > 9999) return console.error("year参数错误");
			if (t <= 0 || t > 12) return console.error("month参数错误");
			var o = new Date(e, t, 0).getTime(),
				i = getDateObject(o, r).date;
			if (n) {
				var u = [];
				i *= 1;
				for (var g = i - 1; g >= 0; g--) {
					var s = getDateObject(o - g * oneDaySeconds, r),
						c = s.year,
						l = s.month,
						d = s.date;
					u.push("text" == a ? c + "年" + l + "月" + d + "日" : "" + c + a + l + a + d)
				}
				return u
			}
			return ["text" == a ? e + "年" + t + "月" + (r ? "01" : 1) + "日" : "" + e + a + t + a + (r ? "01" : 1),
				"text" == a ? e + "年" + t + "月" + i + "日" : "" + e + a + t + a + i
			]
		},
		getThisMonthDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-",
				r = getDateObject(null, t),
				a = r.year,
				o = r.month;
			return TIMER.getYearMonthDates(a, o, e, t, n)
		},
		getLastMonthDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-",
				r = getDateObject((new Date).setDate(1) - oneDaySeconds, t),
				a = r.year,
				o = r.month;
			return TIMER.getYearMonthDates(a, o, e, t, n)
		},
		getNextMonthDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-",
				r = getDateObject(null, t),
				a = r.year,
				o = r.month,
				i = getDateObject(new Date(a, o, 1) + oneDaySeconds, t),
				u = i.year,
				g = i.month;
			return TIMER.getYearMonthDates(u, g, e, t, n)
		},
		getRangeWeekDates: function() {
			for (var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0, t = arguments.length >
					1 && void 0 !== arguments[1] && arguments[1], n = !(arguments.length > 2 && void 0 !==
						arguments[2]) || arguments[2], r = arguments.length > 3 && void 0 !== arguments[3] ?
					arguments[3] : "-", a = new Date, o = a.getDay(), i = a.getTime() + 7 * e * oneDaySeconds,
					u = [], g = 0; g < 7; g++) {
				var s = getDateObject(i + (g - (o + 6) % 7) * oneDaySeconds, n),
					c = s.year,
					l = s.month,
					d = s.date;
				u.push("text" == r ? c + "年" + l + "月" + d + "日" : "" + c + r + l + r + d)
			}
			return t ? u : [u[0], u[6]]
		},
		getNextWeekDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-";
			return TIMER.getRangeWeekDates(1, e, t, n)
		},
		getLastWeekDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-";
			return TIMER.getRangeWeekDates(-1, e, t, n)
		},
		getThisWeekDates: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
				t = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1],
				n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "-";
			return TIMER.getRangeWeekDates(0, e, t, n)
		},
		getTomorrow: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-",
				n = getDateObject(TIMER.getStamp() + 864e5, e),
				r = n.year,
				a = n.month,
				o = n.date;
			return "text" == t ? r + "年" + a + "月" + o + "日" : "" + r + t + a + t + o
		},
		getYesterday: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-",
				n = getDateObject(TIMER.getStamp() - 864e5, e),
				r = n.year,
				a = n.month,
				o = n.date;
			return "text" == t ? r + "年" + a + "月" + o + "日" : "" + r + t + a + t + o
		},
		getToday: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-";
			return TIMER.getYearMonthDate(e, t)
		},
		getYear: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getFullYear();
			return 1 * (e ? t : t.toString().substr(2))
		},
		getMonth: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getMonth() + 1;
			return e && t < 10 && (t = "0" + t), t
		},
		getDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getDate();
			return e && t < 10 && (t = "0" + t), t
		},
		getYearMonth: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-",
				n = new Date,
				r = n.getFullYear(),
				a = n.getMonth() + 1;
			return e && a < 10 && (a = "0" + a), "text" == t ? r + "年" + a + "月" : "" + r + t + a
		},
		getYearMonthDate: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-",
				n = new Date,
				r = n.getFullYear(),
				a = n.getMonth() + 1,
				o = n.getDate();
			return e && (a < 10 && (a = "0" + a), o < 10 && (o = "0" + o)), "text" == t ? r + "年" + a + "月" + o +
				"日" : "" + r + t + a + t + o
		},
		getDateTime: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "-",
				n = !(arguments.length > 2 && void 0 !== arguments[2]) || arguments[2],
				r = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : ":",
				a = new Date,
				o = a.getFullYear(),
				i = a.getMonth() + 1,
				u = a.getDate(),
				g = a.getHours(),
				s = a.getMinutes(),
				c = a.getSeconds();
			return e && (i < 10 && (i = "0" + i), u < 10 && (u = "0" + u)), n && (g < 10 && (g = "0" + g), s < 10 &&
				(s = "0" + s), c < 10 && (c = "0" + c)), ("text" == t ? o + "年" + i + "月" + u + "日" : "" + o +
				t + i + t + u) + " " + ("text" == r ? g + "时" + s + "分" + c + "秒" : "" + g + r + s + r + c)
		},
		getTime: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : ":",
				n = new Date,
				r = n.getHours(),
				a = n.getMinutes(),
				o = n.getSeconds();
			return e && (r < 10 && (r = "0" + r), a < 10 && (a = "0" + a), o < 10 && (o = "0" + o)), "text" == t ?
				r + "时" + a + "分" + o + "秒" : "" + r + t + a + t + o
		},
		getHours: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getHours();
			return e && t < 10 && (t = "0" + t), t
		},
		getMinutes: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getMinutes();
			return e && t < 10 && (t = "0" + t), t
		},
		getSeconds: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getMinutes();
			return e && t < 10 && (t = "0" + t), t
		},
		getStamp: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0],
				t = (new Date).getTime();
			return e ? t : parseInt(t / 1e3)
		},
		getWeek: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
				t = [
					["周天", "周一", "周二", "周三", "周四", "周五", "周六"],
					["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
					["礼拜天", "礼拜一", "礼拜二", "礼拜三", "礼拜四", "礼拜五", "礼拜六"],
					["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
					["Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"]
				],
				n = (new Date).getDay();
			return e = parseInt(e), (isNaN(e) || e < 0 || e > 4) && (e = 0), t[e][n]
		},
		getDiff: function() {
			var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
				t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
				n = e.toString().length;
			if (n < 10) return 1 == t ? "" : "刚刚";
			var r = n > 11,
				a = TIMER.getStamp(r),
				o = parseInt((a - e) / (r ? 1e3 : 1)),
				i = 1 == t ? "" : "刚刚";
			o <= 60 && (i = 1 == t ? "" : "刚刚");
			var u = new Date(e * (r ? 1 : 1e3)),
				g = u.getFullYear(),
				s = u.getMonth(),
				c = u.getDate(),
				l = u.getHours(),
				d = u.getMinutes(),
				v = u.getSeconds();
			return s < 10 && (s = "0" + s), c < 10 && (c = "0" + c), l < 10 && (l = "0" + l), d < 10 && (d = "0" +
				d), v < 10 && (v = "0" + v), o > 60 && o <= 3600 && (i = 1 == t ? l + ":" + d : (Math.ceil(o /
				60) || 1) + "分钟前"), o > 3600 && o <= 86400 && (i = 1 == t ? l + ":" + d : Math.ceil(o / 3600) +
				"小时前"), o > 86400 && o <= 604800 && (i = 1 == t ? s + "-" + c + " " + l + ":" + d : Math.ceil(
				o / 86400) + "天前"), o > 604800 && (i = 1 == t ? g + "-" + s + "-" + c + " " + l + ":" + d : g +
				"-" + s + "-" + c), i
		},
		roll: function(e) {
			var t = {
					number: e.number || 0,
					step: e.step || 1,
					start: e.start || 0,
					speed: e.speed || 10,
					formate: e.formate || !1,
					callback: e.change || function() {}
				},
				n = setInterval(function() {
					t.start += t.step;
					var e = t.start.toLocaleString();
					if (t.callback instanceof Function && t.callback(t.formate ? e : t.start), t.start >= t
						.number) return t.callback instanceof Function && t.callback(t.formate ? t.number
						.toLocaleString() : t.number), clearInterval(n)
				}, t.speed)
		},
		tasks: [],
		start: function() {
			var e = !(arguments.length > 0 && void 0 !== arguments[0]) || arguments[0];
			TIMER.reset = e, timers.forEach(function(e) {
				return clearTimeout(e)
			}), timers = [], setTimeout(function() {
				return TIMER.start(e)
			}, 1e3), TIMER.tasks.forEach(function(e) {
				if (e.delay > 0) delayExec(e);
				else {
					if (e.time) {
						var t = TIMER.getDateTime(),
							n = e.time.split(" "),
							r = _slicedToArray(n, 2),
							a = r[0],
							o = r[1];
						return isDate(a) && isTime(o) && t == e.time && e.func(e.param), isDate(a) && !
							isTime(o) && t == e.time + " 00:00:00" && e.func(e.param), void(isTime(a) && t
								.split(" ")[1] == e.time && e.func(e.param))
					}
					if (e.execTime) {
						var i = TIMER.getStamp();
						i - e.execTime >= e.interval && (e.func(e.param), e.execTime = i)
					} else e.func(e.param), e.execTime = TIMER.getStamp()
				}
			})
		},
		remove: function(e) {
			e instanceof Array ? TIMER.tasks = TIMER.tasks.filter(function(t) {
				return !e.includes(t.id)
			}) : TIMER.tasks = TIMER.tasks.filter(function(t) {
				return t.id != e
			})
		},
		clear: function() {
			TIMER.tasks = []
		},
		delay: function(e) {
			e instanceof Array ? e.forEach(function(e) {
				delayExec(initParams(e))
			}) : e instanceof Object && delayExec(initParams(e))
		},
		chain: function(e) {
			if (TIMER.reset && TIMER.clear(), e instanceof Array) {
				var t;
				(t = TIMER.tasks).push.apply(t, _toConsumableArray(e.map(function(e) {
					return initParams(e)
				})))
			} else e instanceof Object && TIMER.tasks.push(initParams(e))
		},
		countdown: function(e) {
			function t(e, t) {
				var r = countdownObjects[e];
				n(t, r.change, r.done);
				var a = setInterval(function() {
					if (t <= 0) return clearInterval(a);
					t--, r.current = t, n(t, o, i)
				}, 1e3);
				r.timer = a
			}

			function n(e, t, n) {
				var r = e,
					a = 0,
					o = 0,
					i = 0,
					u = 0,
					g = {
						day: 86400,
						hour: 3600,
						minute: 60
					};
				r >= g.day && (a = parseInt(r / g.day), r -= a * g.day), r >= g.hour && r < g.day && (o = parseInt(
					r / g.hour), r -= o * g.hour), r >= g.minute && r < g.hour && (i = parseInt(r / g.minute),
					r -= i * g.minute), r < g.minute && (u = r), o < 10 && (o = "0" + o), i < 10 && (i = "0" +
					i), u < 10 && (u = "0" + u), t instanceof Function && t({
					days: a,
					hours: o,
					minutes: i,
					seconds: u
				}), a + o + i + u <= 0 && n instanceof Function && n()
			}
			var r = e.id || TIMER.getStamp().toString(),
				a = e.seconds || 0,
				o = e.change || function() {},
				i = e.done || function() {};
			return a = parseInt(a), a = isNaN(a) ? 0 : a, countdownObjects[r] ? (countdownObjects[r].timer &&
				clearInterval(countdownObjects[r].timer), countdownObjects[r] = {
					input: a,
					current: a,
					change: o,
					done: i
				}, t(r, a)) : (countdownObjects[r] = {
				input: a,
				current: a,
				change: o,
				done: i
			}, countdownObjects[r].pause = function() {
				clearInterval(countdownObjects[r].timer)
			}, countdownObjects[r].start = function() {
				var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0;
				countdownObjects[r].pause(), t(r, e || countdownObjects[r].current)
			}, countdownObjects[r].restart = function() {
				var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0;
				countdownObjects[r].pause(), t(r, e || countdownObjects[r].input)
			}, t(r, a), countdownObjects[r])
		}
	};
exports.default = TIMER;
