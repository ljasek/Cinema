function updateTotal() {
    var basic = 0;
    var add = 0;
    var tickets = 0;
    var form = document.getElementById('form');
    var checkboxes = form.getElementsByClassName('addon');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            add += 12;
            tickets += 1;
        }
    }
    var p = basic + add;
    var price = p + " zł";
    document.getElementById('total').innerHTML = price;
    document.getElementById('tickets').innerHTML = tickets;
}

document.getElementById('form').addEventListener('change', updateTotal);

updateTotal();
