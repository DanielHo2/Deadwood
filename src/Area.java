public class Area {
	private int x;
	private int y;
	private int h;
	private int w;
	
	Area (int nx, int ny, int nh, int nw)
	{
		x = nx;
		y = ny;
		h = nh;
		w = nw;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getHeight()
	{
		return h;
	}

	public int getWidth()
	{
		return w;
	}
}
