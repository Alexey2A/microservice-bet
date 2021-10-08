function getResult() {

  fetch('/api/calculator',
  {
    headers: {
        'Content-Type': 'application/json'
    },
    method: "POST",
    body: JSON.stringify({
         number1: document.getElementById("number1").value,
         number2: document.getElementById("number2").value
    })
  })
  .then((response) => {
          return response.json();
        })
  .then((data) => {
      document.getElementById("result").innerHTML = data;
    });
}