package tetris;

import java.util.ArrayList;
import java.util.Random;

/**
 *  符合俄罗斯方块规范的方块（Tetromino）随机生成器。
 *  用于防止同一种形状的方块连续多次出现（7-bag 随机机制）。
 *
 *  @author Erik Nelson
 */

public class BagRandomizer {

  private Random random;

  // 当前“袋子（bag）”中的方块值列表。
  ArrayList<Integer> values;

  // 袋子的总容量。
  int capacity;

  public BagRandomizer(Random r, int n) {
    this.random = r;
    this.capacity = n;

    refillValues();
  }

  /**
   * 重置袋子中的值，使其包含从 0（包含）到 capacity（不包含）的整数。
   */
  private void refillValues() {
    ArrayList<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < this.capacity; i++) {
      newValues.add(i);
    }
    values = newValues;
  }

  /**
   * 从袋子中随机抽取并移除一项。如果袋子为空，则重新装满。
   * @return 被移除的整数
   */
  public int getValue() {
    if (values.isEmpty()) {
      refillValues();
    }

    int randomIndex = random.nextInt(values.size());
    int randomValue = values.get(randomIndex);

    values.remove(randomIndex);
    return randomValue;
  }
}
