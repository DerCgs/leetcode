package com.dercg.leetcode.code.a021_MergeTwoSortedLists;

public class main {
    /**
     * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
     * <p>
     * 示例：
     * <p>
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        ListNode result = mergeTwoLists(l1, l2);
        System.out.println(result);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null, result = null;

        while (l1 != null || l2 != null) {
            int addVal = 0;
            if (l1 == null) {
                addVal = l2.val;
                l2 = l2.next;
            } else if (l2 == null) {
                addVal = l1.val;
                l1 = l1.next;
            } else if (l1.val < l2.val) {
                addVal = l1.val;
                l1 = l1.next;
            } else {
                addVal = l2.val;
                l2 = l2.next;
            }
            if (head == null) {
                head = new ListNode(addVal);
                result = head;
            } else {
                head.next = new ListNode(addVal);
                head = head.next;
            }
        }
        return result;
    }

    // 迭代
    public static ListNode mergeTwoListv2(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode result = head;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    head.next = l1;
                    l1 = l1.next;
                } else {
                    head.next = l2;
                    l2 = l2.next;
                }
                head = head.next;
                continue;
            }

            if (l1 != null) {
                head.next = l1;
                l1 = null;
            }
            if (l2 != null) {
                head.next = l2;
                l2 = null;
            }
        }
        return result.next;
    }

    // 递归
    public ListNode mergeTwoListsv3(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }
        else if (l1.val < l2.val) {
            l1.next = mergeTwoListsv3(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoListsv3(l1, l2.next);
            return l2;
        }

    }
}