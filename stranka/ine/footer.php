<div id="footer">
<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST' && $_POST['contact_form']){
if(!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)){
    echo "<div class='error'>Nesprávne vyplnené údaje.</div>";
}else{
    $to_email = "otuzovanie.wz@gmail.com";
    $email = $_POST['email'];
    $headers = "Od: ".$email;
    if($_POST['predmet'] && $_POST['sprava']){
    $predmet = $_POST['predmet'];
    $sprava = $_POST['sprava'];
    $sprava .= " ".$headers;
    if(mail($to_email,$predmet,$sprava,$headers)){
        echo "<div class='success'>Vaša správa bola úspešne odoslaná.</div>";
    }
    }else{
        echo "<div class='error'>Nesprávne vyplnené údaje.</div>";
    }
}
}
    ?>
<div class="kontakt">
<form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="post" class="contact_form">

<h2>Kontaktný formulár</h2>

<label for="email">Váš email:</label>
<input type="text" name="email" id="email"><br><br>

<label for="subject">Predmet</label>
<input type="text" name="predmet" id="subject"><br><br>

<label for="message">Správa</label>
<textarea name="sprava" id="message" placeholder="Priestor pre vaše názory, postrehy, návrhy, pripomienky, zmeny..."></textarea><br><br>

<input type="submit" name="contact_form" value="Odoslat">
</form>
</div>
