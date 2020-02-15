package jfw.util.ecs;

public interface Visitor<T> {

	void visit(Integer id, T component);

}
