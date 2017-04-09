/**
 * 
 */

var curEdited = null;
var preEditCount;

$(document).on('click', 'button.editCount', function (ev) {
	if (curEdited != null) {
		return;
	}
	
	curEdited = ev.target;
	preEditCount = $(curEdited).parent().siblings("td.countCell").first().text();
	
	$(curEdited).attr("hidden", "true");
	$(curEdited).siblings("button.accept").removeAttr("hidden");
	$(curEdited).siblings("button.cancel").removeAttr("hidden");
	$(curEdited).parent().siblings("td.countCell").attr("contenteditable", "true");
});

$(document).on('click', 'button.accept', function (ev) {
	if (curEdited == null) {
		return;
	}
	
	var count = parseInt($(curEdited).parent().siblings("td.countCell").first().text());
	if (count <= 0 || isNaN(count)) {
		$(curEdited).siblings("p.errorMsg").removeAttr("hidden");
		$(curEdited).siblings("p.errorMsg").text("Nieprawidłowa ilość!");
		return;
	}
	window.location.replace("ChangeCartCount?id=" + $(curEdited).parent().parent().attr("data-product-id") + "&count=" + count);
});

$(document).on('click', 'button.cancel', function (ev) {
	if (curEdited == null) {
		return;
	}

	$(curEdited).removeAttr("hidden");
	$(curEdited).siblings("button.accept").attr("hidden", "true");
	$(curEdited).siblings("button.cancel").attr("hidden", "true");
	$(curEdited).siblings("p.errorMsg").attr("hidden", "true");
	$(curEdited).parent().siblings("td.countCell").removeAttr("contenteditable");
	
	$(curEdited).parent().siblings("td.countCell").text(preEditCount);
	
	curEdited = null;
});

$(document).on('click', 'button.delete', function (ev) {

	window.location.replace("DeleteFromCart?id=" + $(ev.target).parent().parent().attr("data-product-id"));
});