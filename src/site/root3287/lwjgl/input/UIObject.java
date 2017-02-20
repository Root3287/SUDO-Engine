package site.root3287.lwjgl.input;

import sun.swing.UIAction;

public abstract class UIObject {
	private int x, y, width, height;
	public UIAction action;
	private String text;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public abstract void render();
	public abstract void update();
	public void setAction(UIAction action){
		this.action = action;
	}
	public UIAction getAction(){
		return this.action;
	}
}