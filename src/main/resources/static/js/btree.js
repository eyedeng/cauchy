const Color = {
    1: 'red',
    2: 'orange',
    3: 'yellow',
    4: 'green',
    5: 'blue',
    6: 'black',
    7: 'white'
};
const Width = 1000;
const Height = 570;
const svg = d3.select('svg');
svg.attr('width', Width)
    .attr('height', Height);

let frames;
let tree;
let idx = 0;
// 操作状态
let state;
const States = {
    create: 1,
    inorder: 2,
    search: 3,
    preOrder: 4
};
document.querySelector("#create").onclick = () => {
    // console.log('按');
    let input = document.querySelector("#input").value;
    input = input.split(",").map(i => parseInt(i));

    let formData = {
        array: input
    };

    $.ajax({
        type : "POST",
        contentType : "application/json",  // 必需
        url : "api/btree/create",
        data : JSON.stringify(formData),
        dataType : 'json',
        success : function(response) {
            // console.log(response);
            frames = response.frames;
            idx = 0;
            state = States.create;
            creatDraw();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};
// let changes;
document.querySelector("#inorder").onclick = () => {

    $.ajax({
        type : "GET",
        url : "api/btree/inOrder",
        success : function(response) {
            // changes = response.changes;
            // inOrderDraw();
            frames = response.frames;
            idx = 0;
            state = States.inorder;
            drawFrames();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};
document.querySelector("#search").onclick = () => {
    let num = document.querySelector("#num").value;
    num = parseInt(num);
    $.ajax({
        type : "GET",
        url : "api/btree/search",
        data: {num: num},
        success : function(response) {
            frames = response.frames;
            // console.log(frames);
            idx = 0;
            state = States.search;
            drawFrames();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};
document.querySelector("#preorder").onclick = () => {

    $.ajax({
        type : "GET",
        url : "api/btree/preOrder",
        success : function(response) {
            frames = response.frames;
            // console.log(frames);
            idx = 0;
            state = States.preOrder;
            drawFrames();
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};

let durationT = 1000;
let timeout = 1000;
let isPause = false;
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
    // pause时
    if (isPause) {
        idx = idx - 1;
        drawOneFrame();
    }
};
document.querySelector('#nextFrame').onclick = () => {
    // pause时
    if (isPause) {
        idx = idx + 1;
        drawOneFrame();
    }
};
document.querySelector('#clearing').onclick = () => {
    if (idx !== 0) {
        idx = 0;
        state = States.create;
        drawOneFrame();
    }
};

let vertexGroup ;
let edgeGroup;
let vertexTextGroup ;
function creatDraw() {
    tree = frames[idx];
    // console.log(tree);
    d3.selectAll('g').remove();
    vertexGroup = svg.append('g').attr('id', 'v');
    edgeGroup = svg.append('g').attr('id', 'e');
    vertexTextGroup = svg.append('g').attr('id', 't');
    // const r = 15;
    vertexGroup.selectAll('circle').data(tree.vertexGroup).enter().append('circle')
        .attr('cx', d => d.cx )
        .attr('cy', d => d.cy )
        .attr('r', d => d.r )
        .attr('fill', d => Color[d.fill])
        .attr('stroke', d => Color[d.stroke] )
        .attr('id', d => d.id);
    edgeGroup.selectAll('line').data(tree.edgeGroup).enter().append('line')
        .attr('x1', d => d.x1 )
        .attr('y1', d => d.y1 )
        .attr('x2', d => d.x2 )
        .attr('y2', d => d.y2 )
        .attr('stroke', d => Color[d.stroke] )
        .attr('stroke-width', 1)
        .attr('id', d => d.id);
    vertexTextGroup.selectAll('text').data(tree.vertexTextGroup).enter().append('text')
        .attr('x', d => d.x - 5)
        .attr('y', d => d.y + 5)
        .attr('stroke', d => Color[d.stroke])
        .text(d => d.text)
        .attr('id', d => d.id);
}

// function drawByIdx() {
//     console.log(idx);
//     let circle = changes[idx].circle;
//     let line = changes[idx].line;
//     let text = changes[idx].text;
//     if (circle != null) {
//         d3.select(`#${circle.id}`)
//             .transition()
//             .duration(durationT)
//             .attr('stroke', Color[circle.stroke])
//             .attr('fill', Color[circle.fill]);
//     }
//     if (text != null) {
//         d3.select(`#${text.id}`)
//             .transition()
//             .duration(durationT)
//             .attr('stroke', Color[text.stroke]);
//     }
//     if (line != null) {
//         d3.select(`#${line.id}`)
//             .transition()
//             .duration(durationT)
//             .attr('stroke', Color[line.stroke])
//             .attr('stroke-width', 3);
//     }
// }

// function inOrderDraw() {
//
//     let intervalID = setInterval(() => {
//         if (idx >= changes.length || isPause) {
//             console.log('clear', idx);
//             // 当前帧为idx-1
//             if (isPause) {
//                 idx = idx - 1;
//             }
//             clearInterval(intervalID);
//         } else {
//             drawByIdx();
//             idx = idx + 1;
//         }
//     }, timeout);
// }

function drawFrames() {
    let intervalID = setInterval(() => {
        if (idx >= frames.length || isPause) {
            console.log('clear', idx);
            // 当前帧为idx-1
            if (isPause) {
                idx = idx - 1;
            }
            clearInterval(intervalID);
        } else {
            drawOneFrame();
            idx = idx + 1;
        }
    }, timeout);
}

function drawOneFrame() {
    console.log(idx);
    tree = frames[idx];
    vertexGroup.selectAll('circle').data(tree.vertexGroup)
        .transition()
        .duration(durationT)
        .attr('cx', d => d.cx )
        .attr('cy', d => d.cy )
        .attr('r', d => d.r )
        .attr('fill', d => Color[d.fill])
        .attr('stroke', d => Color[d.stroke] );
    edgeGroup.selectAll('line').data(tree.edgeGroup)
        .transition()
        .duration(durationT)
        .attr('x1', d => d.x1 )
        .attr('y1', d => d.y1 )
        .attr('x2', d => d.x2 )
        .attr('y2', d => d.y2 )
        .attr('stroke', d => Color[d.stroke] )
        .attr('stroke-width', () => state > 1? 2: 1);
    vertexTextGroup.selectAll('text').data(tree.vertexTextGroup)
        .transition()
        .duration(durationT)
        .attr('x', d => d.x - 5)
        .attr('y', d => d.y + 5)
        .attr('stroke', d => Color[d.stroke])
        .attr('font-weight', 'bold')
        .attr('font-size', 20)
        .text(d => d.text);

}

    // let h = 80, r = 18;

// svg.append('line')
//     .attr('x1', Width / 2)
//     .attr('y1', h)
//     .attr('x2', Width / 3)
//     .attr('y2', h * 2)
//     .attr('stroke', 'red');

// svg.append('circle')
//     .attr('cx', Width / 2)
//     .attr('cy', h)
//     .attr('r', r)

// svg.append('circle')
//     .attr('cx', Width / 3)
//     .attr('cy', h * 2)
//     .attr('r', r)
//     .attr('stroke', 'black')
//     .attr('fill', 'white');
// svg.append('circle')
//     .attr('cx', Width / 3 * 2)
//     .attr('cy', h * 2)
//     .attr('r', r);

// svg.append('circle')
//     .attr('cx', Width / 5)
//     .attr('cy', h * 3)
//     .attr('r', r);
// svg.append('circle')
//     .attr('cx', Width / 5 * 2)
//     .attr('cy', h * 3)
//     .attr('r', r);
// svg.append('circle')
//     .attr('cx', Width / 5 * 3)
//     .attr('cy', h * 3)
//     .attr('r', r);
// svg.append('circle')
//     .attr('cx', Width / 5 * 4)
//     .attr('cy', h * 3)
//     .attr('r', r);

