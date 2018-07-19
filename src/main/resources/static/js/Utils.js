var Consts = {};

var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
function ajax(url, callback) {
    xhr.onreadystatechange = function() {
        if (xhr.readyState==4 && xhr.status==200) {
            callback && callback(xhr.responseText);
        }
    };
    url += "&t=" + Math.random();
    var isAsync = Boolean(callback);
    xhr.open("GET", url, isAsync);
    xhr.send();
    if (!isAsync)
        return xhr.responseText;
}

//Server side: 
//String name = request.getParameter("name");
//name = new String(name.getBytes("ISO8859-1"), "UTF-8");
function ajaxGet(url, callback) {
    xhr.onreadystatechange = function() {
        if (xhr.readyState==4 && xhr.status==200) {
            callback && callback(xhr.responseText);
        }
    };
    url += "&t=" + Math.random();
    var isAsync = Boolean(callback);
    xhr.open("GET", url, isAsync);
    xhr.send();
    if (!isAsync)
        return xhr.responseText;
}

//Client side: <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
//Server side: response.setContentType("text/html;charset=UTF-8");
//@data: var data = "name=" + v("name") + "&brand=" + v("brand"); 
function ajaxPost(url, data, callback) {
    xhr.onreadystatechange = function() {
        if (xhr.readyState==4 && xhr.status==200) {
        	if (callback)
        	    callback(xhr.responseText);
        }
    };
    url += "&t=" + Math.random();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhr.send(data);
}

function getCookie(cookieName) {
    if (document.cookie.length > 0 ) {
        startIndex = document.cookie.indexOf(cookieName + "=");
        if (startIndex != -1) {
            startIndex = startIndex + cookieName.length + 1;
            endIndex = document.cookie.indexOf(";", startIndex);
            if (endIndex == -1) 
                endIndex = document.cookie.length;
            return unescape(document.cookie.substring(startIndex,endIndex));
        }
    }
    return "";
}

/* expireDays: If not set, the lifecycle is the browser session. Ended when browser is closed. */
function setCookie(cookieName, cookieValue, expireDays) {
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() + expireDays);
    var cookieExpires = (expireDays == null) ? "" : ";expires=" + expireDate.toGMTString();
    document.cookie = cookieName + "=" + escape(cookieValue) + cookieExpires;
}

function formateDateTime(dateTime) {
	var d = new Date();
	d.setTime(dateTime);
	return d.format("yyyy-MM-dd hh:mm:ss");
}


function getElement(elementId) {
	return document.getElementById(elementId);
}

function e(elementId) {
	var obj = document.getElementById(elementId);
	if (!obj) {
		var arr = document.getElementsByTagName(elementId);
		if (arr.length > 0)
			obj = arr[0];
	}
	return obj;
}
function v(elementId) {
	return document.getElementById(elementId).value.trim();
}

function $e(elementId) {
	return document.getElementById(elementId);
}
function $v(elementId) {
	return document.getElementById(elementId).value.trim();
}

function isIE() {
    return window.ActiveXObject || "ActiveXObject" in window;
}

/*
function isIE() { return !-[1,]; }

//不支持 IE11
function isIE() {
    return window.navigator.userAgent.indexOf("MSIE") >= 1;
}
*/

function checkBrowser() {
    var mUserAgent = navigator.userAgent.toLowerCase();
    var s;
    (s = mUserAgent.match(/msie ([\d.]+)/)) ? Consts.ie = s[1] :
    (s = mUserAgent.match(/firefox\/([\d.]+)/)) ? Consts.firefox = s[1] :
    (s = mUserAgent.match(/chrome\/([\d.]+)/)) ? Consts.chrome = s[1] :
    (s = mUserAgent.match(/opera.([\d.]+)/)) ? Consts.opera = s[1] :
    (s = mUserAgent.match(/version\/([\d.]+).*safari/)) ? Consts.safari = s[1] : 0;
    Consts.browserVer = Consts.ie ? "IE " + Consts.ie : (Consts.firefox ? "Firefox " + Consts.firefox : (Consts.chrome ? "Chrome " + Consts.chrome : (Consts.opera ? "Opera " + Consts.opera: "Safari " + Consts.safari)));
    
    if (Consts.ie) {
    	var spanBrowserTip = document.getElementById("spanBrowserTip");
    	if (spanBrowserTip)
    		spanBrowserTip.innerHTML = "您的浏览器是 " + Consts.browserVer + "， 推荐使用 Chrome or Firefox。"; 
    }
};
checkBrowser();

function rhex(num) {
	var hex_chr = "0123456789abcdef";
    var str = "";
    for (var j = 0; j <= 3; j++)
        str += hex_chr.charAt((num >> (j * 8 + 4)) & 0x0F) + hex_chr.charAt((num >> (j * 8)) & 0x0F);
    return str;
}

function setSidebarSize() {
    var divSidebar = document.getElementById("divSidebar");
    if (divSidebar) 
    	divSidebar.style.height = document.documentElement.clientHeight + "px";
}


function getNumberWidth(mNumber) {    
	if (mNumber && (typeof mNumber == "number") )
		return parseInt(Math.log(a)/Math.LN10) + 1;
	
	return 0;
}

function padding0(num, width) {
    if (!num) return "";
    if ((num + "").length >= width) return num + "";

    var s = "00000000000000" + num;
    return s.substr(s.length - width, width);
}

function paddingLeft(num, width) {
    if (num == undefined || num == null) return "";
    if ((num + "").length >= width) return num + "";

    /*
    var s = "~~~~~~~~~~~~~~" + num;
    var result = s.substr(s.length - width, width);
    result = result.replace(/~/g, "&nbsp;");
    return result;
    */
    
    var s = "";
    var n = width - ("" + num).length;
    for (var i = 0; i < n; i++) {
        s += "&nbsp;";
    }
    return s + num;
}

function paddingRight(num, width) {
    if (!num) return "";
    if ((num + "").length >= width) return num + "";
   
    var s = "";
    var n = width - ("" + num).length;
    for (var i = 0; i < n; i++) {
        s += "&nbsp;";
    }
    return num + s;
}

function paddingLR(num, width) {
    if (!num) return "";
    if ((num + "").length >= width) return num + "";
   
    var n = width - ("" + num).length;
    var L = (new Number(n/2)).toFixed(0); 
    var R = n - L;

    var strLeft = "", strRight = "";
    for (var i = 0; i < L; i++)
        strLeft = "&nbsp;" + strLeft;
    for (var i = 0; i < R; i++)
        strRight += "&nbsp;";
    return strLeft + num + strRight;
}

function prt() {
    var s = "";
    for (var i = 0; i < arguments.length; i++)
        s += arguments[i] + " ";
    document.write(s);
}
function prtln() {
    for(var i = 0; i < arguments.length; i++)
        document.write(arguments[i] + "<br/>");
    if (arguments.length == 0) document.write("<br/>");
}


function getPropertyNameWidth(obj) {
    var propertyNameWidth = 1;
    for (var a in obj)
        propertyNameWidth = ((a+"").length > propertyNameWidth) ? (a+"").length : propertyNameWidth;

    return propertyNameWidth;
}
/* All propertyName is string, value type is: string, boolean, number, object, undefined, function */
function prtProperties(obj) {
    var propertyNameWidth = getPropertyNameWidth(obj);
    var s = "";
    for (var a in obj) {
        var propertyName = propertyNameWidth ? padding0(a, propertyNameWidth): a;
        var value = obj[a];
        var valueTypeName = paddingLR(typeof value, 10); //type: string, boolean, number, object, undefined, function, the max length is 9

        s += "" + propertyName + ": " + "(" + valueTypeName + ") " + value;
        s += "<br/>";
    }
    if (s == "") s = "&lt;Blank&gt;";
    prt(s);
}

/*
var obj = document.getElementsByTagName("body")[0];
prtTagProperties(obj);
prtTagProperties(obj.style);
*/
function prtTagProperties(obj) {
    var propertyNameWidth = getPropertyNameWidth(obj);
    propertyNameWidth +=2; //for the event, the key show as onClick(), add '(' and ')'

    var events = [], functions = [], objects=[], strs=[], bool=[], num=[], other=[], all=[];
    for (var a in obj) {
        var valueType = typeof obj[a]; 
        if (a.substr(0, 2) == "on")
            events.push(a + "()");
        else if ( valueType == 'function' )
            functions.push(a);
        else if (valueType == 'object')
            objects.push(a);
        else if (valueType == 'string')
            strs.push(a);
        else if (valueType == 'boolean')
            bool.push(a);
        else if (valueType == 'number')
            num.push(a);
        else
            other.push(a);
    }
    events.sort();
    functions.sort();
    objects.sort();
    strs.sort();
    bool.sort();
    num.sort();
    other.sort();
    all = events.concat(functions);
    all = all.concat(objects);
    all = all.concat(strs);
    all = all.concat(bool);
    all = all.concat(num);
    all = all.concat(other);

    var s = "### Properties: ### " + all.length  + "<br/>";
    var index = 0;
    for (var i in all) {
        var key = all[i];
        var value = obj[key];
        var displayValue = (typeof value) == 'function' ? "function() { ...}": value;
        s += paddingLeft(index++, 3) + " : " + paddingLeft(key, propertyNameWidth) + " : " + displayValue + " (" + (typeof value) + ")" + "<br/>";
    }        

    if (s == "") s = "&lt;Blank&gt;";
    prt(s);
}

function addEvent( obj, type, fn ) {
  if ( obj.attachEvent ) {
    obj['e'+type+fn] = fn;
    obj[type+fn] = function(){ obj['e'+type+fn]( window.event );}

    obj.attachEvent( 'on'+type, obj[type+fn] );
  } else
    obj.addEventListener( type, fn, false );
}


//Usage:
//var time1 = new Date().format("yyyy-MM-dd");
//var time2 = new Date().format("yyyy-MM-dd hh:mm:ss");  
Date.prototype.format = function (fmt) {
var o = {
    "M+": this.getMonth() + 1,
    "d+": this.getDate(), 
    "h+": this.getHours(), 
    "m+": this.getMinutes(), 
    "s+": this.getSeconds(), 
    "q+": Math.floor((this.getMonth() + 3) / 3), 
    "S": this.getMilliseconds()
};
if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) 
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
return fmt;
};

//@dateTime millisecond
//@return yyyy-MM-dd hh:mm:ss
function formateDateTime(dateTime) {
	var d = new Date();
	d.setTime(dateTime);
	return d.format("yyyy-MM-dd hh:mm:ss");
}

/* usage: var s = formatDate(new Date()); */
function formatDate(dt) {
	var year = dt.getYear();
	var month = dt.getMonth() + 1;
	var date = dt.getDate();
	var hour = dt.getHours();
	var minute = dt.getMinutes();
	var second = dt.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

/*  Number Add 

    var a = 12.34, b = 13.53;
    prtln(a + b);
    prtln((a + b).toFixed(2));
    prtln(numberAdd(a, b));

    prtln(49999999.99999998.toFixed(2)); //50000000.00

    prtln(new Number(4.2) + new Number(9.2));   //13.399999999999998
    prtln(4.2 + 9.2); //13.399999999999998

    prtln(new Number(4.2) + new Number(9.2));   //13.399999999999998
    prtln(4.2 + 9.4); //13.600000000000001
*/
function numberAdd(arg1,arg2) {
    var r1,r2,m;    
    try{
        r1=arg1.toString().split(".")[1].length;
    }catch(e){
        r1=0;
    }     
    try{
        r2=arg2.toString().split(".")[1].length;
    }catch(e){
        r2=0;
    }
    m=Math.pow(10, Math.max(r1,r2));
    return parseFloat(((arg1*m+arg2*m)/m).toFixed(2));
}

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, '');  
};


/* In IE, can not set table.innerHTML */
function createTableRowsInIE(attendances, currentUserName) {
	var table = document.getElementById("tableAttend");
	//for (var j=0; j < 30; j++)
	for (var i = 0; i < attendances.length; i++) {
		var attend = attendances[i];
		var tr = document.createElement("tr");
		if (attend.userName == currentUserName)
			tr.createAttribute("id", "trCurrentUser");		
//			rowStr = "<tr id='trCurrentUser'>";
		
		tr.appendChild(createTD(attend.userName));
		tr.appendChild(createTD(attend.stationOn));
		//tr.appendChild(createTD(attend.updateTimeStr));		
		tr.appendChild(createTD(attend.adultAmount));
		tr.appendChild(createTD(attend.childAmount));
		tr.appendChild(createTD((attend.single ? "是":"否")));
		tr.appendChild(createTD(attend.comments));
		table.tBodies[0].appendChild(tr);
	}
	
	if (attendances.length == 0) {
		var tr = document.createElement("tr");
		tr.appendChild(createTD("No records", 7));
		table.tBodies[0].appendChild(tr);
	}
}

function createTD(value, colspan) {
	var td = document.createElement("td");
	td.innerHTML = value;
	if (colspan)
		td.colspan = colspan;
	return td;
}

function getRandom(min, max) {
    var r = Math.random() * (max - min);
    var re = Math.round(r + min);
    re = Math.max(Math.min(re, max), min)
     
    return re;
}

function json2str(obj) {
    var arr = [];
    
    var fmt = function(s) {
    if (typeof s == 'object' && s != null) 
        return json2str(s);

        return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
    }

    for (var i in obj) 
        arr.push("'" + i + "':" + fmt(obj[i]));
    
    return '{' + arr.join(',') + '}';
}

//id=100&name=Louis&sex=true
function json2Parameter(obj) {
    if (obj) {
        var str = "";
        for (var i in obj) {
            str += str ? "&" : "";
            str += i + "=" + obj[i];
        }
        return str;
    }
    else
        return "";
}

//Closure
//Usage: fade(document.body);
var fade = function(node) {
    var level = 1;
    var step = function() {
        var hex = level.toString(16);
        node.style.backgroundColor = "#FFAA" + hex + hex;
        if (level < 15) {
            level +=1;
            setTimeout(step, 200);
        }
    };
 
    setTimeout(step, 100);
};

/* aa.txt,bb.txt,cc.txt, delete one item, aa.txt or bb.txt */
function deleteStrItem(source, deleteItem) {
    var reg = new RegExp("(" + deleteItem + ",)|" + "(," + deleteItem + ")");
    return source.replace(reg, "");	
}

//www.abc.com/User?method=save&id=100&name=ABC, return { method: save, id: 100, name: 'ABC'}
function getUrlParameter() {
    var parameter = {};
    
    var str = window.location.href;
    var index = str.indexOf("?");
    if (index > 0) {
    	var ss = str.substr(index + 1); //taskManage.html?projectId=100
    	var aa = ss.split("&");
        for (var i = 0; i < aa.length; i++) {
            var idName = aa[i].split("=");
            parameter[idName[0]] = idName[1];
        }
    }
    
    return parameter;
}

function drawCombo(elementId, records, noNeedBlankLine) {
    var combo = e(elementId);
    //Draw first blank row
    var mOption = document.createElement('option');
    mOption.text = "";
    mOption.value = "";
    combo.add(mOption, null);

    for (var i = 0; i < records.length; i++) {
        var mOption = document.createElement('option');
        mOption.text = records[i].name;
        mOption.value = records[i].id;
        combo.add(mOption, null);
    }
}

window.console = window.console || (function(){ 
    var c = {}; 
    c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function(){}; 
    return c; 
})();
function log(content) {
    var d1 = new Date();
    var str = d1.getFullYear() + "-" + (d1.getMonth() + 1) + "-" + d1.getDate() + " " + d1.getHours() + ":" + d1.getMinutes() + ":" + d1.getSeconds() + "." + d1.getMilliseconds();
    str += " " + content;
    console.log(str);
}