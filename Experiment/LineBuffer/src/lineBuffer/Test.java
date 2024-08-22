package lineBuffer;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<LatLng> datas = new ArrayList<>();
		datas.add(new LatLng(36.52, 117.33));
		datas.add(new LatLng(36.71, 118.41));
		datas.add(new LatLng(37.13, 119.33));
		String result = PolylineBuffer.getLineBufferEdgeCoords(datas, 0.1);
		System.out.println(result);
		String strs[] = result.split(",");
		for (int i = 0; i < strs.length / 2; i++) {
			System.out.println(strs[2 * i] + "," + strs[2 * i + 1]);
		}

	}

}
