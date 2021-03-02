
let maze;
// 操作状态
const States = {
    create: 1,
    bfs: 2,
    dfs: 3,
};
document.querySelector("#create").onclick = () => {
    clearInterval(intervalID);

    let input = document.querySelector("#input").value;
    input = eval('(' + input + ')');  // string to array
    console.log(input);

    let formData = {
        arrays: input
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",  // 必需
        url: "api/graph/create",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (response) {
            // console.log(response);
            frames = response.frames;
            idx = 0;
            state = States.create;
            creatDraw();
        },
        error: function (e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};

document.querySelector("#bfs").onclick = () => {
    clearInterval(intervalID);

    $.ajax({
        type : "GET",
        url : "/api/graph/bfs",
        success : function(response) {

            frames = response.frames;
            idx = 0;
            state = States.bfs;
            drawFrames();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};
document.querySelector("#dfs").onclick = () => {
    clearInterval(intervalID);

    $.ajax({
        type : "GET",
        url : "/api/graph/dfs",
        success : function(response) {
            frames = response.frames;
            // console.log(frames);
            idx = 0;
            state = States.dfs;
            drawFrames();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};


let rectGroup;
let circleOne;
function creatDraw() {
    maze = frames[idx];
    console.log(maze);
    d3.selectAll('g').remove();
    rectGroup = svg.append('g');
    circleOne = svg.append('g');

    circleOne.selectAll('circle').data([maze.circle]).enter().append('circle')
        .attr('cx', d => d.cx )
        .attr('cy', d => d.cy )
        .attr('r', 0)  // 第一帧不显示
        .attr('fill', d => Color[d.fill])
        .attr('stroke', d => Color[d.stroke] );

    rectGroup.selectAll('rect').data(maze.rectGroup).enter().append('rect')
        .attr('x', d => d.x )
        .attr('y', d => d.y )
        .attr('width', d => d.width )
        .attr('height', d => d.height )
        .attr('fill', d => Color[d.fill] )
        .attr('stroke', "black" );

}

function drawOneFrame() {
    console.log(idx);
    maze = frames[idx];

    circleOne.selectAll('circle').data([maze.circle])
        .transition()
        .duration(durationT)
        .attr('cx', d => d.cx )
        .attr('cy', d => d.cy )
        .attr('r', d => d.r )
        .attr('fill', d => Color[d.fill])
        .attr('stroke', d => Color[d.stroke] );

    rectGroup.selectAll('rect').data(maze.rectGroup)
        .transition()
        .duration(durationT)
        .attr('x', d => d.x )
        .attr('y', d => d.y )
        .attr('width', d => d.width )
        .attr('height', d => d.height )
        .attr('fill', d => Color[d.fill] )
        .attr('stroke', "black" );

}
