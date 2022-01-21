package com.whaa.blog.datastructure;

/**
 * 贪心算法
 * created by wangzelong 2021-12-24 11:03
 */
public class GreedyAlgorithm {

    public static void main(String[] args) {
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm();
        int minCoinCount = greedyAlgorithm.getMinCoinCount();
        int minCoinCountOfValue = greedyAlgorithm.getMinCoinCountOfValue();
        System.out.println(minCoinCount);
        System.out.println(minCoinCountOfValue);
    }

    int getMinCoinCountHelper(int total, int[] values, int valueCount) {
        int rest = total;
        int count = 0;

        // 从大到小遍历所有面值
        for (int i = 0; i < valueCount; ++i) {
            int currentCount = rest / values[i]; // 计算当前面值最多能用多少个
            rest -= currentCount * values[i]; // 计算使用完当前面值后的余额
            count += currentCount; // 增加当前面额用量

            if (rest == 0) {
                return count;
            }
        }

        return -1; // 如果到这里说明无法凑出总价，返回-1
    }

    private int getMinCoinCount() {
        int[] values = {5, 3}; // 硬币面值
        int total = 11; // 总价
        return getMinCoinCountHelper(total, values, 2); // 输出结果
    }

    int getMinCoinCountOfValue(int total, int[] values, int valueIndex) {
        int valueCount = values.length;
        if (valueIndex == valueCount) {
            return Integer.MAX_VALUE;
        }

        int minResult = Integer.MAX_VALUE;
        int currentValue = values[valueIndex];
        int maxCount = total / currentValue;

        for (int count = maxCount; count >= 0; count--) {
            int rest = total - count * currentValue;

            // 如果rest为0，表示余额已除尽，组合完成
            if (rest == 0) {
                minResult = Math.min(minResult, count);
                break;
            }

            // 否则尝试用剩余面值求当前余额的硬币总数
            int restCount = getMinCoinCountOfValue(rest, values, valueIndex + 1);

            // 如果后续没有可用组合
            if (restCount == Integer.MAX_VALUE) {
                // 如果当前面值已经为0，返回-1表示尝试失败
                if (count == 0) {
                    break;
                }
                // 否则尝试把当前面值-1
                continue;
            }

            minResult = Math.min(minResult, count + restCount);
        }

        return minResult;
    }

    int getMinCoinCountLoop(int total, int[] values, int k) {
        int minCount = Integer.MAX_VALUE;
        int valueCount = values.length;

        if (k == valueCount) {
            return Math.min(minCount, getMinCoinCountOfValue(total, values, 0));
        }

        for (int i = k; i <= valueCount - 1; i++) {
            // k位置已经排列好
            int t = values[k];
            values[k] = values[i];
            values[i] = t;
            minCount = Math.min(minCount, getMinCoinCountLoop(total, values, k + 1)); // 考虑后一位

            // 回溯
            t = values[k];
            values[k] = values[i];
            values[i] = t;
        }

        return minCount;
    }

    int getMinCoinCountOfValue() {
        int[] values = {5, 3}; // 硬币面值
        int total = 11; // 总价
        int minCoin = getMinCoinCountLoop(total, values, 0);

        return (minCoin == Integer.MAX_VALUE) ? -1 : minCoin;  // 输出答案
    }
}
