<!DOCTYPE html>
<html lang="sk">
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-162012046-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-162012046-1');
</script>

    <meta charset="UTF-8">
    <title>Otuzovanie</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="shortcut icon" href="obrazky/otuzovanie_icon.ico" type="image/x-icon">
    <meta name="description" content="Na tejto stránke vám ponúkame základy otužovania a tiež možnosť zaznamenávania vašich výsledkov, ako aj prehľad miest na otužovanie na mape.">
    <meta name="keywords" content="otuzovanie, studena voda, chlad, zima, vysledky, zaklady otuzovania, ako zacat s otuzovanim, zaznamenavanie, casy">
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-app.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-auth.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-database.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-firestore.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.2.9/firebase-analytics.js"></script>
</head>
<body>
<?php $actual_page = "tabulka"; ?>
    <div id="main">
        <?php include "ine/header.php"; ?>
        <div class="odsadenie">

        <div class="text alignCenter">
        <h1>Zoznam otužovaní</h1>

        <div id="bezZaznamu"></div>
        <table class="zaznamy pagination" data-pagecount="3" id="zaznamy">
            <tr>
                <th id="datumSort"><a id="dat" class="sortable">Dátum</a></th>
                <th id="casSort"><a id="cas" class="sortable">Čas</a></th>
                <th id="casVoVodeSort"><a id="cvv" class="sortable">Čas vo vode</a></th>
                <th id="vodaSort"><a id="vod" class="sortable">Voda</a></th>
                <th id="vzduchSort"><a id="vzd" class="sortable">Vzduch</a></th>
                <th>Akcie</th>
            </tr>
        </table>
        <div id="loader" class="loader"></div>
        <div id="paginator" class="paginator">
            <a id="previous"><<</a>
            <h4></h4>
            <a id="next">>></a>
        </div>
        </div>
    </div>
</div>
    <?php include "ine/footer.php" ?>
    <script src="javascript/tabulka.js"></script>
    <script src="javascript/prihlaseny.js"></script>
</body>
</html>