$(document).ready(function () {
    var menu = (window.location.pathname).split('/')[1];

    if (menu === 'general') {
        $('#mainNavbar > ul:nth-child(1) > li:nth-child(1)').addClass('active');
        console.log("exe");
    }

});