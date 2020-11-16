package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.constant.Color;
import com.eyedeng.cauchy.domain.Frame;
import com.eyedeng.cauchy.dto.Array;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/sort")
public class SortController {

    @PostMapping("select")
    public List<Frame> selectSort(@RequestBody Array input) {
        //  int a[] = {2,10,6,4,5};
        Integer[] a = input.getArray();
//        System.out.println(a);
        ArrayList<Frame> frames = new ArrayList<>();
        Frame init = new Frame(a);
        frames.add(init);

        int N = a.length;
        Frame pre = init;
        for (int i = 0; i < N - 1; i++) {
            int minIdx = i;

            pre = new Frame(pre);
            pre.array[minIdx].color = Color.RED;    // 当前最小值
            frames.add(pre);

            for (int j = i + 1; j < N; j++) {

                pre = new Frame(pre);
                pre.array[j].color = Color.GREEN;   // 待比较值
                if (j-1 != minIdx)
                    pre.array[j-1].color = Color.BLUE;  // 复原
                frames.add(pre);

                if (a[j] < a[minIdx]) {
                    pre = new Frame(pre);
                    pre.array[minIdx].color = Color.BLUE;  // 复原

                    minIdx = j;

                    pre.array[minIdx].color = Color.RED;  // 新最小值
                    frames.add(pre);
                }
            }
            pre = new Frame(pre);
            if (N-1 != minIdx)
                pre.array[N-1].color = Color.BLUE;
            pre.array[i].color = Color.RED;  // 待交换
            frames.add(pre);

            if (minIdx != i) {
                swap(a, minIdx, i);

                pre = new Frame(pre);
                pre.array[minIdx].value = a[minIdx];
                pre.array[i].value = a[i];
                frames.add(pre);
            }

            pre = new Frame(pre);
            pre.array[i].color = Color.ORANGE;  // 已排序
            if (minIdx != i)
                pre.array[minIdx].color = Color.BLUE;
            frames.add(pre);
        }

        pre = new Frame(pre);
        pre.array[N-1].color = Color.ORANGE;
        frames.add(pre);

        return frames;
    }

    private static void swap(Integer[] a, int minIdx, int i) {
        int temp = a[minIdx];
        a[minIdx] = a[i];
        a[i] = temp;
    }
}
