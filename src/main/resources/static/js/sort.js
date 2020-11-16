// 需与btree.js重构

let frames;
let idx = 0;

let durationT = 1000;
let rate = 1;
let timeout = 1000;
let isPause = false;
let intervalID;

document.querySelector("#create").onclick = () => {
    let input = document.querySelector("#input").value;
    input = input.split(",").map(i => parseInt(i));
    console.log(input);
    $.ajax({
        type : "POST",
        contentType : "application/json",  // 必需
        url : "api/sort/select",
        data : JSON.stringify({array: input}),
        dataType : 'json',
        success: function (response) {
            console.log(response);
            frames = response;
            idx = 0;
            createDraw();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};

document.querySelector("#select").onclick = () =>{
    idx = 1;
    // 当未按clear
    isPause = false;
    drawFrames();
};

let widR = 40, heiRMax = 400, dif = 5, sta = 200;
const svg = d3.select('svg');
svg.attr('width', Width)
    .attr('height', heiRMax + 100);
let yScale;

document.querySelector('#pause').onclick = () => {
    if (!isPause)
        isPause = true;
};

document.querySelector('#play').onclick = () => {
    if (isPause) {
        isPause = false;
        drawFrames();
    }
};
document.querySelector('#preFrame').onclick = () => {
    // pause时 且不为0
    if (isPause && idx > 0) {
        idx =  idx - 1;
        drawOneFrame();
    }
};
document.querySelector('#nextFrame').onclick = () => {
    // pause时
    if (isPause && idx < frames.length-1) {
        idx = idx + 1;
        drawOneFrame();
    }
};
document.querySelector('#clearing').onclick = () => {
    if (idx !== 0) {
        idx = 0;
        // 清除此函数
        clearInterval(intervalID);
        drawOneFrame();
    }
};

document.querySelector('#speed2').onclick = () => {rate = 1.5; durationT = rate * durationT; timeout = durationT};
document.querySelector('#speed3').onclick = () => {rate = 1;  durationT = rate * durationT; timeout = durationT};
document.querySelector('#speed4').onclick = () => {rate = 0.5;  durationT = rate * durationT; timeout = durationT};

function createDraw() {
    yScale = d3.scaleLinear()
        .domain([0, d3.max( frames[0].array , d => d.value)])
        .range([0, heiRMax]);

    svg.selectAll('rect').data(frames[idx].array).enter().append('rect')
         .transition()
         .duration(600)
         .attr('x',(d, i) => (i+1) * (widR+dif) + sta)
         .attr('y', 50)
         .attr('width', widR)
         .attr('height', d => yScale(d.value))
         .attr("fill", d => Color[d.color]);
    svg.selectAll('text').data(frames[idx].array).enter().append('text')
        .transition()
        .duration(600)
        .attr('x',(d, i) => (i+1) * (widR+dif) + sta + widR / 2)
        .attr('y', 38)
        .attr("fill", d => Color[d.color])
        .text(d => d.value)
        .attr('font-size', 20)
        .attr('text-anchor', 'middle');
}
function drawOneFrame() {
    console.log(idx);
    svg.selectAll('rect').data(frames[idx].array)
        .transition()
        .duration(durationT)
        .attr('x',(d, i) => (i+1) * (widR+dif) + sta)
        .attr('y', 50)
        .attr('width', widR)
        .attr('height', d => yScale(d.value))
        .attr("fill", d => Color[d.color]);
    svg.selectAll('text').data(frames[idx].array)
        .transition()
        .duration(durationT)
        .attr('x',(d, i) => (i+1) * (widR+dif) + sta + widR / 2)
        .attr('y', 38)
        .attr("fill", d => Color[d.color])
        .text(d => d.value)
        .attr('font-size', 20)
        .attr('text-anchor', 'middle');
}
function drawFrames() {

    // 异步
    intervalID = setInterval(() => {
        if (idx >= frames.length || isPause) {
            console.log('clear', idx);
            if (isPause && idx > 0) {
                idx = idx - 1;
            }
            clearInterval(intervalID);
        }
        else{
            drawOneFrame();
            idx = idx + 1;
        }
    }, timeout);

}