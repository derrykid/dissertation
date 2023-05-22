const start = new Date()
const startString = new Date().toLocaleTimeString()
const startPlus1 = new Date(start.getTime() + 1000 * 60).toLocaleTimeString();
var data = [
    [startString, 0.8, 0.7, 0.7],
    [startPlus1, 0.9, 0.3, 0.4],
];

anychart.onDocumentReady(function () {
    // create a data set
    var dataSet = anychart.data.set(data);

    // map the data for all series
    var firstSeriesData = dataSet.mapAs({x: 0, value: 1});
    var secondSeriesData = dataSet.mapAs({x: 0, value: 2});
    var thirdSeriesData = dataSet.mapAs({x: 0, value: 3});

    var chart = anychart.line();

    // create the series and name them
    var firstSeries = chart.line(firstSeriesData);
    firstSeries.name("Roger Federer");
    var secondSeries = chart.line(secondSeriesData);
    secondSeries.name("Rafael Nadal");
    var thirdSeries = chart.line(thirdSeriesData);
    thirdSeries.name("Novak Djokovic");


    chart.legend().enabled(true);

    chart.title("Big Three's Grand Slam Title Race");
    chart.container("container");

    chart.crosshair().enabled(true).yStroke(null).yLabel(false);

    chart.tooltip().positionMode("point");
    chart.tooltip().position("right").anchor("left-center").offsetX(5).offsetY(5);

    // draw the resulting chart
    chart.draw();

    function updateChart(newData) {
        chart.data(newData);
        chart.draw()
    }

    setTimeout(function () {
        let newData;

        axios.post('http://localhost:8123/chartData')
            .then(response => {
                // Handle the response data here
                matches = response.data;
                let now = new Date()
                let plus1 = new Date(now.getTime() + 1000 * 60)
                let plus2 = new Date(now.getTime() + 2000 * 60)
                let plus3 = new Date(now.getTime() + 3000 * 60)
                //matches[0]['bookmakers'][0]['homeProbability']
                newData = [
                    [now, 0.9, 0.8, 0.8],
                    [plus1, 0.3, 0.7, 0.3],
                    [plus2, 0.6, 0.9, 0.4],
                    [plus3, 0.6, 0.9, 0.4],
                ];
            })
            .catch(error => {
                // Handle any error that occurred during the request
                console.error(error);
            });


        updateChart(newData);
    }, 3000);

});
