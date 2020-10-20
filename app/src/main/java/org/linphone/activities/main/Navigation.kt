/*
 * Copyright (c) 2010-2020 Belledonne Communications SARL.
 *
 * This file is part of linphone-android
 * (see https://www.linphone.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.linphone.activities.main

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import org.linphone.R
import org.linphone.activities.main.chat.fragments.DetailChatRoomFragment
import org.linphone.activities.main.chat.fragments.MasterChatRoomsFragment
import org.linphone.activities.main.contact.fragments.ContactEditorFragment
import org.linphone.activities.main.contact.fragments.MasterContactsFragment
import org.linphone.activities.main.history.fragments.DetailCallLogFragment
import org.linphone.activities.main.history.fragments.MasterCallLogsFragment
import org.linphone.activities.main.settings.fragments.SettingsFragment
import org.linphone.activities.main.sidemenu.fragments.SideMenuFragment
import org.linphone.contact.NativeContact
import org.linphone.core.Address

internal fun Fragment.findMasterNavController(): NavController {
    return if (!resources.getBoolean(R.bool.isTablet)) {
        findNavController()
    } else {
        parentFragment?.parentFragment?.findNavController() ?: findNavController()
    }
}

fun getRightToLeftAnimationNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.enter_right)
        .setExitAnim(R.anim.exit_left)
        .setPopEnterAnim(R.anim.enter_left)
        .setPopExitAnim(R.anim.exit_right)
        .build()
}

fun getLeftToRightAnimationNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.enter_left)
        .setExitAnim(R.anim.exit_right)
        .setPopEnterAnim(R.anim.enter_right)
        .setPopExitAnim(R.anim.exit_left)
        .build()
}

/* Chat  related */

internal fun MasterChatRoomsFragment.navigateToChatRoom() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.masterChatRoomsFragment) {
            findNavController().navigate(R.id.action_masterChatRoomsFragment_to_detailChatRoomFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.chat_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_detailChatRoomFragment)
    }
}

internal fun MasterChatRoomsFragment.navigateToChatRoomCreation(
    createGroupChatRoom: Boolean = false
) {
    val bundle = bundleOf("createGroup" to createGroupChatRoom)
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.masterChatRoomsFragment) {
            findNavController().navigate(
                R.id.action_masterChatRoomsFragment_to_chatRoomCreationFragment,
                bundle
            )
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.chat_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_chatRoomCreationFragment, bundle)
    }
}

internal fun DetailChatRoomFragment.navigateToContacts(sipUriToAdd: String) {
    val deepLink = "linphone-android://contact/new/$sipUriToAdd"
    findMasterNavController().navigate(Uri.parse(deepLink), getLeftToRightAnimationNavOptions())
}

internal fun DetailChatRoomFragment.navigateToChatRooms() {
    val deepLink = "linphone-android://chat/"
    findMasterNavController().navigate(Uri.parse(deepLink), getLeftToRightAnimationNavOptions())
}

/* Contacts  related */

internal fun MasterContactsFragment.navigateToContact() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.masterContactsFragment) {
            findNavController().navigate(R.id.action_masterContactsFragment_to_detailContactFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.contacts_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_detailContactFragment)
    }
}

internal fun MasterContactsFragment.navigateToContactEditor(sipUriToAdd: String? = null) {
    val bundle = if (sipUriToAdd != null) bundleOf("SipUri" to sipUriToAdd) else Bundle()
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.masterContactsFragment) {
            findNavController().navigate(R.id.action_masterContactsFragment_to_contactEditorFragment, bundle)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.contacts_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_contactEditorFragment, bundle)
    }
}

internal fun ContactEditorFragment.navigateToContact(contact: NativeContact) {
    val deepLink = "linphone-android://contact/view/${contact.nativeId}"
    if (!resources.getBoolean(R.bool.isTablet)) {
        findMasterNavController().navigate(Uri.parse(deepLink), getRightToLeftAnimationNavOptions())
    } else {
        findMasterNavController().navigate(Uri.parse(deepLink))
    }
}

/* History  related */

internal fun MasterCallLogsFragment.navigateToCallHistory() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.masterCallLogsFragment) {
            findNavController().navigate(R.id.action_masterCallLogsFragment_to_detailCallLogFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.history_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_detailCallLogFragment)
    }
}

internal fun DetailCallLogFragment.navigateToContacts(sipUriToAdd: String) {
    val deepLink = "linphone-android://contact/new/$sipUriToAdd"
    findMasterNavController().navigate(Uri.parse(deepLink), getRightToLeftAnimationNavOptions())
}

internal fun DetailCallLogFragment.navigateToContact(contact: NativeContact) {
    val deepLink = "linphone-android://contact/view/${contact.nativeId}"
    findMasterNavController().navigate(Uri.parse(deepLink), getRightToLeftAnimationNavOptions())
}

internal fun DetailCallLogFragment.navigateToFriend(friendAddress: Address) {
    val deepLink = "linphone-android://contact/new/${friendAddress.asStringUriOnly()}"
    findMasterNavController().navigate(Uri.parse(deepLink), getRightToLeftAnimationNavOptions())
}

/* Settings  related */

internal fun SideMenuFragment.navigateToAccountSettings(identity: String) {
    if (!resources.getBoolean(R.bool.isTablet)) {
        // If not a tablet, navigate directly to account settings fragment
        val deepLink = "linphone-android://account-settings/$identity"
        findNavController().navigate(Uri.parse(deepLink))
    } else {
        // On tablet, to keep the categories list on left side, navigate to settings fragment first
        val deepLink = "linphone-android://settings/$identity"
        findNavController().navigate(Uri.parse(deepLink))
    }
}

internal fun SettingsFragment.navigateToAccountSettings(identity: String) {
    val bundle = bundleOf("Identity" to identity)
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(
                R.id.action_settingsFragment_to_accountSettingsFragment,
                bundle
            )
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_accountSettingsFragment, bundle)
    }
}

internal fun SettingsFragment.navigateToTunnelSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_tunnelSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_tunnelSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToAudioSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_audioSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_audioSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToVideoSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_videoSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_videoSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToCallSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_callSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_callSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToChatSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_chatSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_chatSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToNetworkSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_networkSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_networkSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToContactsSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_contactsSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_contactsSettingsFragment)
    }
}

internal fun SettingsFragment.navigateToAdvancedSettings() {
    if (!resources.getBoolean(R.bool.isTablet)) {
        if (findNavController().currentDestination?.id == R.id.settingsFragment) {
            findNavController().navigate(R.id.action_settingsFragment_to_advancedSettingsFragment)
        }
    } else {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.settings_nav_container) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_global_advancedSettingsFragment)
    }
}