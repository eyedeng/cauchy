// 需与btree.js重构
// 操作状态
const States = {
    create: 1
};
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
svg.attr('width', Width)
    .attr('height', heiRMax + 100);
let yScale;


function createDraw() {
    yScale = d3.scaleLinear()
        .domain([0, d3.max( frames[0].array , d => d.value)])
        .range([0, heiRMax]);

    d3.selectAll('rect').remove();
    d3.selectAll('text').remove();

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
