$(document).ready(function () {

    var aDogsPurpose = 'https://www.youtube.com/embed/e6BVkNwL3Rk';
    var gold = 'https://www.youtube.com/embed/sdD8jNA2epY';
    var hacksawRidge = 'https://www.youtube.com/embed/v5n3LvsKPBU';
    var wyklety = 'https://www.youtube.com/embed/91azPGsQeRY';
    var t2Trainspotting = 'https://www.youtube.com/embed/dAz8cwVH92c';
    var silence = 'https://www.youtube.com/embed/pAiZkwInXCk';
    var passengers = 'https://www.youtube.com/embed/7BWWWQzTpNU';
    var theJungleBook = 'https://www.youtube.com/embed/T_EN03fJIyY';

    embedVideo();

    function embedVideo() {
        switch ($('.film-name').text()) {
            case 'Byl sobie pies':
                $('iframe').attr('src', aDogsPurpose);
                break;
            case 'Gold':
                $('iframe').attr('src', gold);
                break;
            case 'Przelecz ocalonych':
                $('iframe').attr('src', hacksawRidge);
                break;
            case 'Wyklety':
                $('iframe').attr('src', wyklety);
                break;
            case 'T2 Trainspotting':
                $('iframe').attr('src', t2Trainspotting);
                break;
            case 'Milczenie':
                $('iframe').attr('src', silence);
                break;
            case 'Pasazerowie':
                $('iframe').attr('src', passengers);
                break;
            case 'Ksiega dzungli':
                $('iframe').attr('src', theJungleBook);
                break;
        }
    }
});