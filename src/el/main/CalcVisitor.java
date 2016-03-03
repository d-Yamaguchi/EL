package el.main;

public abstract class CalcVisitor {
	public abstract Object visit(Add node);

	public abstract Object visit(Sub node);

	public abstract Object visit(Mul node);

	public abstract Object visit(Div node);
	
	public abstract Object visit(Equals node);
	
	public abstract Object visit(NotEquals node);
	
	public abstract Object visit(GreaterThan node);
	
	public abstract Object visit(GreaterThanEquals node);
	
	public abstract Object visit(LessThan node);
	
	public abstract Object visit(LessThanEquals node);
	
	public abstract Object visit(Int node);
	
	public abstract Object visit(If node);
}
