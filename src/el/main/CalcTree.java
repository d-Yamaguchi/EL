package el.main;
import java.util.ArrayList;
import java.util.List;

import nez.ast.CommonTree;

public abstract class CalcTree {
	List<CalcTree> child;

	public CalcTree() {
		this.child = new ArrayList<>();
	}

	public abstract Object accept(CalcVisitor visitor);

}

abstract class BinaryExpr extends CalcTree {

	public BinaryExpr(CalcTree left, CalcTree right) {
		super();
		this.child.add(left);
		this.child.add(right);
	}
}

class Add extends BinaryExpr {

	public Add(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class Sub extends BinaryExpr {

	public Sub(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class Mul extends BinaryExpr {

	public Mul(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class Div extends BinaryExpr {

	public Div(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class Equals extends BinaryExpr {

	public Equals(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class NotEquals extends BinaryExpr {

	public NotEquals(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class GreaterThan extends BinaryExpr {

	public GreaterThan(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class GreaterThanEquals extends BinaryExpr {

	public GreaterThanEquals(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class LessThan extends BinaryExpr {

	public LessThan(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class LessThanEquals extends BinaryExpr {

	public LessThanEquals(CalcTree left, CalcTree right) {
		super(left, right);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}

class If extends CalcTree {

	public If(CalcTree left, CalcTree right, CalcTree rright) {
		super();
		this.child.add(left);
		this.child.add(right);
		this.child.add(rright);
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}


class Int extends CalcTree {
	int val;

	public Int(int val) {
		this.val = val;
	}

	@Override
	public Object accept(CalcVisitor visitor) {
		return visitor.visit(this);
	}

}