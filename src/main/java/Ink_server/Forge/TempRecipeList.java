package Ink_server.Forge;

import java.util.Arrays;

public class TempRecipeList {
    private final String[] slotItems = new String[]{null,null,null,null,null,null,null};
    private final int[] slotAmounts = new int[]{0,0,0,0,0,0,0};

    public TempRecipeList(String[] strings, int[] ints) {
        if (strings != null){
            System.arraycopy(strings, 0, slotItems, 0, strings.length);
        }
        if (ints != null){
            System.arraycopy(ints, 0, slotAmounts, 0, ints.length);
        }
    }
    public void setSlotItems(int i, String id){
        slotItems[i] = id;
    }
    public void setSlotAmounts(int i, int num){
        slotAmounts[i] = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempRecipeList that = (TempRecipeList) o;
        // 比较两个数组的内容是否完全一致
        return Arrays.equals(slotItems, that.slotItems) && Arrays.equals(slotAmounts, that.slotAmounts);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(slotItems);
        result = 31 * result + Arrays.hashCode(slotAmounts);
        return result;
    }
}
