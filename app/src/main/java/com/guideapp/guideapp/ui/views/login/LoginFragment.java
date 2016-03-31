package com.guideapp.guideapp.ui.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.guideapp.guideapp.R;

public class LoginFragment extends Fragment
    implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = LoginFragment.class.getName();

    private Button mRegister;
    private ImageView mLoginGoogle;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initGoogleAuth();
        setFindViewById(view);
        setViewProperties();

        return view;
    }

    /**
     * Initialize google auth
     */
    public void initGoogleAuth(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .enableAutoManage(this.getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * Initialize views by id
     */
    public void setFindViewById(View view) {
        mRegister = (Button) view.findViewById(R.id.register_button);
        mLoginGoogle = (ImageView) view.findViewById(R.id.login_google_button);
    }


    /**
     * Initialize view's listener
     */
    private void setViewProperties() {
        mRegister.setOnClickListener(
                v -> {
                    //startActivity(new Intent(getContext(), RegisterActivity.class))
                    Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
                });

        mLoginGoogle.setOnClickListener(
                v -> {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            if(acct != null) {
                Log.d(TAG, "handleSignInResult:" + acct.getDisplayName());
                Log.d(TAG, acct.getPhotoUrl().toString());
                Log.d(TAG, acct.getEmail());
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult.getErrorMessage());
    }
}
