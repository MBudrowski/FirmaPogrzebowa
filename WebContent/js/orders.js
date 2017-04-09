/**
 * 
 */

$(document).on('click', 'button.expand', function (ev) {

	$(ev.target).parent().parent().next("tr").children("td").children(".orderDetails").first().slideToggle();
	
});

/*$(document).on('click', 'button.payOnlineOrder', function (ev) {

	var id = $(ev.target).parent().siblings("td.orderId").first().text().substring(1);
	window.location.replace('payOnline.html?id=' + id + '&type=p');
	
});

$(document).on('click', 'button.payOnlineFuneral', function (ev) {

	var id = $(ev.target).parent().siblings("td.orderId").first().text().substring(1);
	window.location.replace('payOnline.html?id=' + id + '&type=f');
	
});*/