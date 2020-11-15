const Color = {
    1: 'red',
    2: 'orange',
    3: 'yellow',
    4: 'green',
    5: 'blue',
    6: 'black',
    7: 'white'
};
//const treeFrame =
//{"frames":[{"edgeGroup":[{"x1":500,"y1":80,"x2":260,"y2":160,"stroke":6},{"x1":500,"y1":80,"x2":740,"y2":160,"stroke":6},{"x1":260,"y1":160,"x2":140,"y2":240,"stroke":6},{"x1":260,"y1":160,"x2":380,"y2":240,"stroke":6},{"x1":140,"y1":240,"x2":60,"y2":320,"stroke":6},{"x1":380,"y1":240,"x2":300,"y2":320,"stroke":6}],"vertexGroup":[{"cx":500,"cy":80,"r":15,"fill":7,"stroke":6},{"cx":260,"cy":160,"r":15,"fill":7,"stroke":6},{"cx":740,"cy":160,"r":15,"fill":7,"stroke":6},{"cx":140,"cy":240,"r":15,"fill":7,"stroke":6},{"cx":380,"cy":240,"r":15,"fill":7,"stroke":6},{"cx":60,"cy":320,"r":15,"fill":7,"stroke":6},{"cx":300,"cy":320,"r":15,"fill":7,"stroke":6}],"vertexTextGroup":[{"x":500,"y":80,"text":"84"},{"x":260,"y":160,"text":"24"},{"x":740,"y":160,"text":"87"},{"x":140,"y":240,"text":"13"},{"x":380,"y":240,"text":"57"},{"x":60,"y":320,"text":"9"},{"x":300,"y":320,"text":"56"}]}]};
const Width = 1000;
const Height = 600;
const svg = d3.select('svg');
svg.attr('width', Width)
    .attr('height', Height);

document.querySelector("#create").onclick = () => {
    // console.log('按');
    let input = document.querySelector("#input").value;
    input = input.split(",").map(i => parseInt(i));

    let formData = {
        array: input
    };

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "api/btree/create",
        data : JSON.stringify(formData),
        dataType : 'json',
        success : function(response) {
            // console.log(response);
            creatDraw(response.frames[0]);
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};
let chs;
document.querySelector("#inorder").onclick = () => {

    $.ajax({
        type : "GET",
        url : "api/btree/inOrder",
        success : function(response) {
            chs = response.changes;
            console.log(chs);
            inOrderDraw(response.changes);
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
};

// tree = treeFrame.frames[0];

function creatDraw(tree) {
    // console.log(tree);
    d3.selectAll('g').remove();
    const vertexGroup = svg.append('g').attr('id', 'v');
    const edgeGroup = svg.append('g').attr('id', 'e');
    const vertexTextGroup = svg.append('g').attr('id', 't');
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
        .attr('id', d => d.id);
    vertexTextGroup.selectAll('text').data(tree.vertexTextGroup).enter().append('text')
        .attr('x', d => d.x - 5)
        .attr('y', d => d.y + 5)
        .attr('stroke', d => Color[d.stroke])
        .text(d => d.text)
        .attr('id', d => d.id);
}

function inOrderDraw(changes) {
    let idx = 0;
    let durationT = 1000;
    let intervalID = setInterval(() => {
        console.log(idx);
        if (idx >= changes.length) {
            clearInterval(intervalID);
        } else {
            let circle = changes[idx].circle;
            let line = changes[idx].line;
            let text = changes[idx].text;
            if (circle != null) {
                d3.select(`#${circle.id}`)
                    .transition()
                    .duration(durationT)
                    .attr('stroke', Color[circle.stroke])
                    .attr('fill', Color[circle.fill]);
            }
            if (text != null) {
                d3.select(`#${text.id}`)
                    .transition()
                    .duration(durationT)
                    .attr('stroke', Color[text.stroke]);
            }
            if (line != null) {
                d3.select(`#${line.id}`)
                    .transition()
                    .duration(durationT)
                    .attr('stroke', Color[line.stroke])
                    .attr('stroke-width', 3);
            }
        }
        idx = idx + 1;
    }, 1000);
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

