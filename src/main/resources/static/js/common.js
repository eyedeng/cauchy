// 公共操作的可复用代码

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

let frames;   // 动画帧
let idx = 0;  // 第几帧
let state;    // 当前运行的算法

// 操控面板
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
        state = States.create;
        drawOneFrame();
    }
};

// timeout = durationT
const timePerFrame = 1000;  // 1 second/frame
let durationT = timePerFrame;
let rate = 1;
document.querySelector('#speed2').onclick = () => {rate = 1.5; durationT = rate * timePerFrame; };
document.querySelector('#speed3').onclick = () => {rate = 1;  durationT = rate * timePerFrame; };
document.querySelector('#speed4').onclick = () => {rate = 0.5;  durationT = rate * timePerFrame; };
document.querySelector('#speed5').onclick = () => {rate = 0.25;  durationT = rate * timePerFrame; };

// 异步动画
let intervalID;
function drawFrames() {
    intervalID = setInterval(() => {
        if (idx >= frames.length || isPause) {
            console.log('clear', idx);
            // 当前帧为idx-1 注意溢出
            if (isPause && idx > 0) {
                idx = idx - 1;
            }
            clearInterval(intervalID);
        } else {
            drawOneFrame();
            idx = idx + 1;
        }
    }, durationT);
}