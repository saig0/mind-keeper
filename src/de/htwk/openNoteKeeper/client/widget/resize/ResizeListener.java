package de.htwk.openNoteKeeper.client.widget.resize;

/**
 * @author Vince Vice - www.electrosound.tv This is licensed under Apache
 *         License Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */
public interface ResizeListener {
	/**
	 * indicates a Panel has been resized
	 * 
	 * @param width
	 * @param height
	 **/
	public void onResized(Integer width, Integer height);

	/**
	 * after resized ended
	 * 
	 * @param width
	 * @param height
	 */
	public void onReleasedResized(Integer width, Integer height);
}
