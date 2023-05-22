let matches;
function fetchData() {
    axios.post('http://localhost:8123/chartData')
        .then(response => {
            // Handle the response data here
            matches = response.data;
        })
        .catch(error => {
            // Handle any error that occurred during the request
            console.error(error);
        });
}

// Execute fetchData every 5 seconds
setInterval(fetchData, 5000);
