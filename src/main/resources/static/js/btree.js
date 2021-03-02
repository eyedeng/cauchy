
let tree;
// 操作状态
const States = {
    create: 1,
    inorder: 2,
    search: 3,
    preOrder: 4
};
document.querySelector("#create").onclick = () => {
    // console.log('按');
    clearInterval(intervalID);
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
document.querySelector("#inorder").onclick = () => {
    clearInterval(intervalID);
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
    clearInterval(intervalID);
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
    clearInterval(intervalID);
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
    let delayT = 800;
    // const r = 15;
    vertexGroup.selectAll('circle').data(tree.vertexGroup).enter().append('circle')
        .transition()
        .duration(durationT * 0.6)
        .delay((d,i) => delayT * i * rate)
        .attr('cx', d => d.cx )
        .attr('cy', d => d.cy )
        .attr('r', d => d.r )
        .attr('fill', d => Color[d.fill])
        .attr('stroke', d => Color[d.stroke] )
        .attr('id', d => d.id);
    edgeGroup.selectAll('line').data(tree.edgeGroup).enter().append('line')
        .transition()
        .duration(durationT * 0.6)
        .delay((d,i) => delayT * i * rate)
        .attr('x1', d => d.x1 )
        .attr('y1', d => d.y1 )
        .attr('x2', d => d.x2 )
        .attr('y2', d => d.y2 )
        .attr('stroke', d => Color[d.stroke] )
        .attr('stroke-width', 1)
        .attr('id', d => d.id);
    vertexTextGroup.selectAll('text').data(tree.vertexTextGroup).enter().append('text')
        .transition()
        .duration(durationT * 0.6)
        .delay((d,i) => delayT * i * rate)
        .attr('x', d => d.x - 5)
        .attr('y', d => d.y + 5)
        .attr('stroke', d => Color[d.stroke])
        .text(d => d.text)
        .attr('id', d => d.id);
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

