package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.user.client.ui.Image;

public enum IconPool {

	Alert("alert.png"), Alert_Big("alert_big.png"), Warning("warning.png"), Warnung_Big(
			"warning_big.png"), Info("info.png"), Info_Big("info_big.png"), Home(
			"home.png"), Home_Small("home_small.png"), Settings("settings.png"), Settings_Small(
			"settings_small.png"), Admin("admin.png"), Admin_Small(
			"admin_small.png"), Logo("logo.jpg"), Add("add.png"), Remove(
			"remove.png"), Arrow_Right("right_arrow.png"), Arrow_Left(
			"left_arrow.png"), Buddies("buddies.png"), Buddy_Blue(
			"buddy_blue.png"), Buddy_Green("buddy_green.png"), Add_User(
			"add_user.png"), Remove_User("remove_user.png"), User("user.png"), User_Offline(
			"offline_user.png"), Chat_User("chat_user.png"), User_Request(
			"user_request.png"), User_Notification("user_notification.png"), Favourite(
			"favourite.png"), Find("find.png"), Notice("notice.png"), Loading(
			"loading2.gif"), Google("google.jpg"), Google_Transparent(
			"google_transparent.gif"), Trash("trash.png"), Trash_Big(
			"trash_big.png");

	private final String dir = "images/";
	private final String url;

	IconPool(String url) {
		this.url = url;
	}

	public Image createImage() {
		return new Image(dir + url);
	}

	public String getUrl() {
		return createImage().getUrl();
	}
}
