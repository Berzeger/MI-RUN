/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Václav
 */
public class VMConstantPool {

    private final List<VMCPoolItem> items;
    int pointer = 0;

    public VMConstantPool() {
	items = new ArrayList<>();
    }

    public int addItem(VMCPoolItem item) {
	items.add(pointer, item);
	return pointer++;
    }

    public VMCPoolItem getItem(int pos) {
	return items.get(pos);
    }
}
