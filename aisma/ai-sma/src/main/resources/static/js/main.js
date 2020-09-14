//check for token
$(function () {
  if (localStorage.getItem("token")) {
    $("#loginControls").hide();
    $("#logoutControls").show();
    $("#scrapingBtn").removeClass("disabled");
    $("#sentimentBtn").removeClass("disabled");
    $("#resulttBtn").removeClass("disabled");
  } else {
    $("#loginControls").show();
    $("#logoutControls").hide();
    $("#scrapingBtn").addClass("disabled");
    $("#sentimentBtn").addClass("disabled");
    $("#resulttBtn").addClass("disabled");
  }
});
//Logout submition
$("#logoutBtn").click(function (ev) {
  localStorage.removeItem("token");
  location.reload();
});
//Login submition
$("#loginForm").submit(function (event) {
  var settings = {
    url: "http://localhost:8080/auth/login",
    async: false, 
    method: "POST",
    timeout: 0,
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("token"),
    },
    data: JSON.stringify({
      username: $("#username1").val(),
      password: $("#password1").val(),
    }),
  };

  $.ajax(settings)
    .done(function (response) {
      localStorage.setItem("token", "Bearer " + response["token"]);
      location.reload();
    })
    .fail(function (xhr, err) {
      alert("credentials wrongs!");
    });
    event.preventDefault();
});
//Registration submition
$("#registerForm").submit(function (event) {
  var settings = {
    url: "http://localhost:8080/auth/register",
    async: false, 
    method: "POST",
    timeout: 0,
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("token"),
    },
    data: JSON.stringify({
      username: $("#username2").val(),
      password: $("#password2").val(),
    }),
  };

  $.ajax(settings)
    .done(function (response) {
      localStorage.setItem("token", "Bearer " + response["token"]);
      location.reload();
    })
    .fail(function (xhr, err) {
      alert("can't create account");
    });
    event.preventDefault();
});
//Scraping submition
$("#scrapingForm").submit(function (event) {
  var settings = {
    url: "http://localhost:8080/scrap",
    async: false, 
    method: "POST",
    timeout: 0,
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("token"),
    },
    data: JSON.stringify({ tag: $("#tag1").val() }),
  };

  $.ajax(settings)
    .done(function (response) {
      alert("Job started");
      $("#scrapingModal").modal("toggle");
    })
    .fail(function (xhr, err) {
      alert("can't start scraping");
    });
    event.preventDefault();
});
//Scraping stop submition
$("#stopForm").submit(function (event) {
    var settings = {
      url: "http://localhost:8080/scrap",
      async: false, 
      method: "DELETE",
      timeout: 0,
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
      },
      data: JSON.stringify({ tag: $("#tag2").val() }),
    };
  
    $.ajax(settings)
      .done(function (response) {
        alert("Job stoped");
        $("#stopModal").modal("toggle");
      })
      .fail(function (xhr, err) {
        alert("can't stop scraping");
      });
      event.preventDefault();
  });
//SentimentAnalysis submition
$("#sentimentForm").submit(function (event) {
  var settings = {
    url: "http://localhost:8080/sentiment",
    async: false, 
    method: "POST",
    timeout: 0,
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("token"),
    },
    data: JSON.stringify({ tag: $("#tag3").val() }),
  };

  $.ajax(settings)
    .done(function (response) {
      alert("Job started");
      $("#sentimentModal").modal("toggle");
    })
    .fail(function (xhr, err) {
      alert("can't start SentimentAnalysis");
    });
    event.preventDefault();
});
//SentimentAnalysis result
$("#viewResultForm").submit(function (event) {
    var settings = {
      url: "http://localhost:8080/sentiment", 
      method: "PUT",
      timeout: 0,
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
      },
      data: JSON.stringify({ tag: $("#tag4").val() }),
    };
  
    $.ajax(settings)
      .done(function (response) {
        $("#resultModal").modal("toggle");

        var ctx = $('#resultChart');
        var resultChart = new Chart(ctx, {
            type: 'pie',
            data: response,
            options: {}
        });

        $("#canvasModal").modal("toggle");
      })
      .fail(function (xhr, err) {
        alert("can't view sentiment analysis results");
      });
      event.preventDefault();
  });