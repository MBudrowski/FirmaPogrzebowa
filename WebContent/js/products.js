/**
 * 
 */

$(document).on('click', 'button.addToCartButton', function (ev) {
	$.post('AddToCartServlet',
			{
				id: $(ev.target).attr('data-product-id')
			},
			function (data, status) {
				$("p#resultMessage").html('Dodano produkt do koszyka!');
				$("#cartLink").text('Koszyk (' + data + ')');
				setTimeout(function() {
					$("p#resultMessage").html('<br/>');
				}, 5000);
			});
});