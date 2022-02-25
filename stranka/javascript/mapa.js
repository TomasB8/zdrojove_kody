var map = L.map('map', {
  maxBoundsViscosity: 1.0,
  minZoom: 2,
  maxBounds: [
    [-90, -180],
    [90, 180]
  ]
}).setView([48.738611, 19.156944], 8);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors</a>',
  noWrap: true,
  worldCopyJump: true
}).addTo(map);

var text = document.querySelector(".text");
text.style.padding = 0;

map.on("dblclick", function(e){
  var mp = new L.Marker([e.latlng.lat, e.latlng.lng]).addTo(map);
  window.location.href = "pridatMiesto.php?lat=" + e.latlng.lat + "&lng=" + e.latlng.lng;
});


(function(){
  var firebaseConfig = {
    apiKey: "",
    authDomain: "",
    projectId: "",
    storageBucket: "",
    messagingSenderId: "",
    appId: "",
    measurementId: ""
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();

      var database = firebase.database();

      var mapRef = database.ref("miesta");
      mapRef.on('value', (snapshot) => {
        snapshot.forEach(function(childSnapshot){
          let teplota = childSnapshot.val().teplota;
          let datum = childSnapshot.val().datum;
          let date;
          let temp;
          if(datum === ""){
            date = "?";
          }else{
            date = datum;
          }
          if(teplota === "" || teplota == "-654"){
            temp = "?";
          }else{
            temp = teplota.toString().replace(/\./g, ',');
          }
          var marker = L.marker([childSnapshot.val().latitude, childSnapshot.val().longtitude]).addTo(map);
          marker.bindPopup("<a href='upravitMiesto.php?miestoId=" + childSnapshot.val().miestoId + "' id='odkaz'><font size='+1'><b>" + childSnapshot.val().nazov + "</b></font></a> <br>" + temp + " °C" +
            " (" + date + ")");
            marker.addEventListener("dblclick", e => {

            })
        })
      })
    }());

// Initialize and add the map
//function initMap() {
//    // The location of Uluru
//    const uluru = { lat: -25.344, lng: 131.036 };
//    // The map, centered at Uluru
//    const map = new google.maps.Map(document.getElementById("map"), {
//      zoom: 4,
//      center: uluru,
//    });
//    // The marker, positioned at Uluru
//    const marker = new google.maps.Marker({
//      position: uluru,
//      map: map,
//    });
//  }