/**
 * 
 */

$.datetimepicker.setLocale('pl');

$(".datePicker").datetimepicker({
	format : 'Y-m-d',
	timepicker : false
});

$(".dateTimePicker").datetimepicker({
	format : 'Y-m-d H:i',
	timepicker : true
});