package Tree;

public class _94_BinaryTreeInOrderTraversal {

    
    94. Binary Tree Inorder Traversal
[1, null, 2 , 3]
        1
        \
        2
        /
        3
    output: [1, 3, 2]
    Solve: Recursive & iteratively.

            https://youtu.be/Vq_7Ks5QqG4

    Inorder: left -> root -> right 左子树 -> 当前节点 -> 右子树
    二叉树的中序遍历：递归原则 对每一个节点都套用
	3
            /\
            2 5
            /	/\
            1	4 6
            3->2->1 先走左边，1没有左子树，1自己是当前节点，所以记录1	[1]
            1->2 1没有右子树，所以返回到2. 2自己是当前节点，所以记录2	[1, 2]
            2->3 same reason	[1, 2, 3]
            3->5->4 3的右子树是5, 5有左子树4, 所以先进到4	[1, 2, 3, 4]
    剩下的逻辑一样: [1, 2, 3, 4, 5, 6]

    Recursion:
    递归 -> top down dfs() -> dfs是recursion:
            1. base case: null node -> return
            2. dfs(left)
3. add current node to result
4. dfs(right)
--------
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();	// initialize the recursion array
        dfs(root, res);	// call dfs
        return res;
    }

    private void dfs(TreeNode node, List<Integer> res) {
        if (node == null) {	// base case
            return;
        }
        dfs(node.left, res);
        res.add(node.val);
        dfs(node.right, res);
    }
====================
    Iterative:
    模拟recursion。递归本质：程序中的call stack会记录当前function call的状态，后面的递归结束后会返回当前call
    所以我们可以用stack手动模拟call stack

	3
            /\
            2 5
            /	/\		inorder: []
            1	4 6		stack: [1, 2, 3]
    对于每一个节点都先找左子树，初始状态以: stack = [root.leftmost]开始
    leftmost(n):
            while n! = null:
            stack.push(n)
    n = n.left

	3
            /\
            2 5
            /	/\		inorder: [1]
            1	4 6		stack: [1, 2, 3] -> pop 1 栈顶
    因为最左下角的节点不会再有左子树，所以stack顶部元素就是下一个遍历的节点
            cur = stack.pop()
result.add(cur)

            3
            /\
            2 5
            /	/\		inorder: [1, 2]				inorder: [1, 2, 3]
            1	4 6		stack: [2, 3] -> pop 2 		stack: [3] -> pop 3
    按照原则我们应该访问1的右子树，并对右子树套用同样的规则。但是这里1没有右子树，所以下一个访问的是栈顶的2。同样适用于3
    leftmost(cur.right)
result.add(cur)

            3
            /\
            2 5
            /	/\		inorder: [1，2，3, 4]
            1	4 6		stack: [4, 5] -> pop 4 栈顶
    接下去访问3的右子树，并套用同样规则，重复leftmost()操作。5 -> 4, 4是栈顶
            cur = stack.pop()
res.add(cur)
            4没有右子树，返回5，pop 5. inorder: [1，2，3, 4, 5]

    leftmost(cur.right)	// cur = 5
-> leftmost(5) inorder: [1，2，3, 4, 5]; stack: [6] -> pop 6

    Iteration终止条件：stack为空
    初始状态：stack = [nodes until root.leftmost]	// 从root到最左下节点上所有的node
    从root开始：
            - 当前节点 cur = stack.pop()
            - 添加当前节点到答案 res.add(cur)
            - 对右子树套用同样操作 leftmost(cur.right)
---------------------
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();	// return一个空的
        }

        Deque<TreeNode> stack = new ArrayDeque<>();	// initialize stack
        List<Integer> res = new ArrayList<>();	// initialize result
        leftmost(root, stack);	// 找到root的leftmost，再把之间的所有节点（整条path）push到stack上

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            res.add(cur);
            leftmost(cur.right, stack);
        }
        return res;
    }

    private void leftmost(TreeNode node, Deque<TreeNode> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}
