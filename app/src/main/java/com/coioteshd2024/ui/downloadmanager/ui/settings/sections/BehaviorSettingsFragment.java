/* * EasyPlex - Movies - Live Streaming - TV Series, Anime * * @author @Y0bEX * @package EasyPlex - Movies - Live Streaming - TV Series, Anime * @copyright Copyright (c) 2021 Y0bEX, * @license http://codecanyon.net/wiki/support/legal-terms/licensing-terms/ * @profile https://codecanyon.net/user/yobex * @link yobexd@gmail.com * @skype yobexd@gmail.com **/

package com.coioteshd2024.ui.downloadmanager.ui.settings.sections;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.coioteshd2024.R;
import com.coioteshd2024.ui.downloadmanager.core.InputFilterMinMax;
import com.coioteshd2024.ui.downloadmanager.core.RepositoryHelper;
import com.coioteshd2024.ui.downloadmanager.core.settings.SettingsRepository;
import com.coioteshd2024.ui.downloadmanager.core.utils.Utils;
import com.coioteshd2024.ui.downloadmanager.ui.BaseAlertDialog;
import com.takisoft.preferencex.EditTextPreference;
import com.takisoft.preferencex.PreferenceFragmentCompat;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BehaviorSettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener
{
    @SuppressWarnings("unused")
    private static final String TAG = BehaviorSettingsFragment.class.getSimpleName();

    private static final String TAG_CUSTOM_BATTERY_DIALOG = "custom_battery_dialog";

    private SettingsRepository pref;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private BaseAlertDialog.SharedViewModel dialogViewModel;

    public static BehaviorSettingsFragment newInstance()
    {
        BehaviorSettingsFragment fragment = new BehaviorSettingsFragment();
        fragment.setArguments(new Bundle());

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dialogViewModel = new ViewModelProvider(requireActivity()).get(BaseAlertDialog.SharedViewModel.class);

        pref = RepositoryHelper.getSettingsRepository(getActivity().getApplicationContext());

        String keyAutostart = getString(R.string.pref_key_autostart);
        SwitchPreferenceCompat autostart = findPreference(keyAutostart);
        if (autostart != null) {
            autostart.setChecked(pref.autostart());
            bindOnPreferenceChangeListener(autostart);
        }

        String keyCpuSleep = getString(R.string.pref_key_cpu_do_not_sleep);
        SwitchPreferenceCompat cpuSleep = findPreference(keyCpuSleep);
        if (cpuSleep != null) {
            cpuSleep.setChecked(pref.cpuDoNotSleep());
            bindOnPreferenceChangeListener(cpuSleep);
        }

        String keyOnlyCharging = getString(R.string.pref_key_download_only_when_charging);
        SwitchPreferenceCompat onlyCharging = findPreference(keyOnlyCharging);
        if (onlyCharging != null) {
            onlyCharging.setChecked(pref.onlyCharging());
            bindOnPreferenceChangeListener(onlyCharging);
        }

        String keyBatteryControl = getString(R.string.pref_key_battery_control);
        SwitchPreferenceCompat batteryControl = findPreference(keyBatteryControl);
        if (batteryControl != null) {
            batteryControl.setSummary(getString(R.string.pref_battery_control_summary,
                    Utils.getDefaultBatteryLowLevel()));
            batteryControl.setChecked(pref.batteryControl());
            bindOnPreferenceChangeListener(batteryControl);
        }

        String keyCustomBatteryControl = getString(R.string.pref_key_custom_battery_control);
        SwitchPreferenceCompat customBatteryControl = findPreference(keyCustomBatteryControl);
        if (customBatteryControl != null) {
            customBatteryControl.setSummary(getString(R.string.pref_custom_battery_control_summary,
                    Utils.getDefaultBatteryLowLevel()));
            customBatteryControl.setChecked(pref.customBatteryControl());
            bindOnPreferenceChangeListener(customBatteryControl);
        }

        String keyCustomBatteryControlValue = getString(R.string.pref_key_custom_battery_control_value);
        SeekBarPreference customBatteryControlValue = findPreference(keyCustomBatteryControlValue);
        if (customBatteryControlValue != null) {
            customBatteryControlValue.setValue(pref.customBatteryControlValue());
            customBatteryControlValue.setMin(10);
            customBatteryControlValue.setMax(90);
            bindOnPreferenceChangeListener(customBatteryControlValue);
        }

        String keyUnmeteredOnly = getString(R.string.pref_key_umnetered_connections_only);
        SwitchPreferenceCompat unmeteredOnly = findPreference(keyUnmeteredOnly);
        if (unmeteredOnly != null) {
            unmeteredOnly.setChecked(pref.unmeteredConnectionsOnly());
            bindOnPreferenceChangeListener(unmeteredOnly);
        }

        String keyRoaming = getString(R.string.pref_key_enable_roaming);
        SwitchPreferenceCompat roaming = findPreference(keyRoaming);
        if (roaming != null) {
            roaming.setChecked(pref.enableRoaming());
            bindOnPreferenceChangeListener(roaming);
        }

        String keyReplaceDuplicateDownloads = getString(R.string.pref_key_replace_duplicate_downloads);
        SwitchPreferenceCompat replaceDuplicateDownloads = findPreference(keyReplaceDuplicateDownloads);
        if (replaceDuplicateDownloads != null) {
            replaceDuplicateDownloads.setChecked(pref.replaceDuplicateDownloads());
            bindOnPreferenceChangeListener(replaceDuplicateDownloads);
        }

        String keyAutoConnect = getString(R.string.pref_key_auto_connect);
        SwitchPreferenceCompat autoConnect = findPreference(keyAutoConnect);
        if (autoConnect != null) {
            autoConnect.setChecked(pref.autoConnect());
            bindOnPreferenceChangeListener(autoConnect);
        }

        String keyTimeout = getString(R.string.pref_key_timeout);
        EditTextPreference timeout = findPreference(keyTimeout);
        if (timeout != null) {
            timeout.setDialogMessage(R.string.pref_timeout_summary);
            String value = Integer.toString(pref.timeout());
            timeout.setOnBindEditTextListener((editText) ->
                    editText.setFilters(new InputFilter[]{new InputFilterMinMax(0, Integer.MAX_VALUE)}));
            timeout.setSummary(value);
            timeout.setText(value);
            bindOnPreferenceChangeListener(timeout);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        disposables.clear();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        subscribeAlertDialog();
    }

    private void subscribeAlertDialog()
    {
        Disposable d = dialogViewModel.observeEvents()
                .subscribe((event) -> {
                    if (event.dialogTag == null || !event.dialogTag.equals(TAG_CUSTOM_BATTERY_DIALOG))
                        return;
                    if (event.type == BaseAlertDialog.EventType.NEGATIVE_BUTTON_CLICKED)
                        disableCustomBatteryControl();
                });
        disposables.add(d);
    }

    @Override
    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.pref_behavior, rootKey);
    }

    private void bindOnPreferenceChangeListener(Preference preference)
    {
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(final Preference preference, Object newValue)
    {
        if (preference.getKey().equals(getString(R.string.pref_key_autostart))) {
            pref.autostart((boolean)newValue);
            Utils.enableBootReceiver(getActivity(), (boolean)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_cpu_do_not_sleep))) {
            pref.cpuDoNotSleep((boolean)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_download_only_when_charging))) {
            pref.onlyCharging((boolean)newValue);
            if(!((SwitchPreferenceCompat) preference).isChecked())
                disableBatteryControl();

        } else if(preference.getKey().equals(getString(R.string.pref_key_battery_control))) {
            pref.batteryControl((boolean)newValue);
            if(((SwitchPreferenceCompat) preference).isChecked())
                disableCustomBatteryControl();

        } else if(preference.getKey().equals(getString(R.string.pref_key_custom_battery_control))) {
            pref.customBatteryControl((boolean)newValue);
            if (!((SwitchPreferenceCompat) preference).isChecked())
                showCustomBatteryDialog();

        } else if(preference.getKey().equals(getString(R.string.pref_key_custom_battery_control_value))) {
            pref.customBatteryControlValue((int)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_umnetered_connections_only))) {
            pref.unmeteredConnectionsOnly((boolean)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_enable_roaming))) {
            pref.enableRoaming((boolean)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_replace_duplicate_downloads))) {
            pref.replaceDuplicateDownloads((boolean)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_auto_connect))) {
            pref.autoConnect((boolean)newValue);

        } else if(preference.getKey().equals(getString(R.string.pref_key_timeout))) {
            int value = 0;
            if (!TextUtils.isEmpty((String)newValue))
                value = Integer.parseInt((String)newValue);
            pref.timeout(value);
            preference.setSummary(Integer.toString(value));
        }

        return true;
    }

    private void showCustomBatteryDialog()
    {
        FragmentManager fm = getChildFragmentManager();
        if (fm.findFragmentByTag(TAG_CUSTOM_BATTERY_DIALOG) == null) {
            BaseAlertDialog customBatteryDialog = BaseAlertDialog.newInstance(
                    getString(R.string.warning),
                    getString(R.string.pref_custom_battery_control_dialog_summary),
                    0,
                    getString(R.string.yes),
                    getString(R.string.no),
                    null,
                    true);

            customBatteryDialog.show(fm, TAG_CUSTOM_BATTERY_DIALOG);
        }
    }

    private void disableBatteryControl()
    {
        String keyBatteryControl = getString(R.string.pref_key_battery_control);
        SwitchPreferenceCompat batteryControl = findPreference(keyBatteryControl);
        if (batteryControl != null)
            batteryControl.setChecked(false);
        pref.batteryControl(false);
        disableCustomBatteryControl();
    }

    private void disableCustomBatteryControl()
    {
        String keyCustomBatteryControl = getString(R.string.pref_key_custom_battery_control);
        SwitchPreferenceCompat batteryControl = findPreference(keyCustomBatteryControl);
        if (batteryControl != null)
            batteryControl.setChecked(false);
        pref.customBatteryControl(false);
    }
}
