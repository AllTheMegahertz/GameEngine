package collision;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by AllTheMegahertz on 12/22/2017.
 */

public interface Collider {

	Bounding getBounding();
	Vector3f getPosition();

}
