//package com.example.uropproject;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import org.bouncycastle.util.encoders.Hex;
//import org.javatuples.Triplet;
//import java.math.BigInteger;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.interfaces.ECPrivateKey;
//import java.security.interfaces.ECPublicKey;
//import java.security.spec.ECGenParameterSpec;
//import java.security.spec.ECPoint;
//import java.security.spec.EllipticCurve;
//import java.util.ArrayList;
//import java.util.Random;
//import javafx.util.Pair;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AgroaFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AgroaFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private Button agroa_parameter_confirm_button;
//    private Button agroa_number_confirm_button;
//    private EditText agroa_editTextParameter;
//    private EditText agroa_editTextNumber;
//    private static TextView agroa_textView_output;
//    public final static BigInteger TWO = BigInteger.valueOf(2); // constant for scalar operations
//    public final static BigInteger THREE = BigInteger.valueOf(3);
//    public static Integer securityParameter;
//    public static Integer n;
//    public static ArrayList<Pair<BigInteger, BigInteger>> msk;
//    public static ArrayList<BigInteger> w;
//    public static ECPublicKey PublicKey;
//    public static ECPrivateKey PrivateKey;
//    public AgroaFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AgroaFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AgroaFragment newInstance(String param1, String param2) {
//        AgroaFragment fragment = new AgroaFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View fragmentAgroaLayout=inflater.inflate(R.layout.fragment_agroa,container,false);
//        agroa_parameter_confirm_button =fragmentAgroaLayout.findViewById(R.id.agroa_parameter_confirm_button);
//        agroa_number_confirm_button =fragmentAgroaLayout.findViewById(R.id.agroa_number_confirm_button);
//        agroa_editTextParameter =fragmentAgroaLayout.findViewById(R.id.agroa_editTextParameter);
//        agroa_editTextNumber =fragmentAgroaLayout.findViewById(R.id.agroa_editTextNumber);
//        agroa_textView_output=fragmentAgroaLayout.findViewById(R.id.agroa_textView_output);
//        return fragmentAgroaLayout;
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        view.findViewById(R.id.agroa_parameter_confirm_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View view){
//                String TAG=agroa_editTextParameter.getText().toString();
//                securityParameter=Integer.valueOf(agroa_editTextParameter.getText().toString());
//                Log.d(TAG, "index=" + 1);
//
//            }
//
//        });
//        view.findViewById(R.id.agroa_number_confirm_button).setOnClickListener(new View.OnClickListener() {
//
//            private static final String TAG = "MyActivity";
//            public void onClick(View view) {
//               n=Integer.valueOf(agroa_editTextNumber.getText().toString());
//                try {
//                    Setup();
//                    testDKeyGen();
//                    testEncrypt();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (InvalidAlgorithmParameterException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }
//    private static void Setup() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
//        msk=new ArrayList<>();
//        w=new ArrayList<>();
//        for(int i=0;i<n;i++) {
//            BigInteger s_1=nextRandomBigInteger(securityParameter);
//            BigInteger s_2=nextRandomBigInteger(securityParameter);
//            Pair<BigInteger, BigInteger> s=new Pair<>(s_1,s_2);
//            msk.add(s);
//            w.add(nextRandomBigInteger(securityParameter));
//        }
//        KeyPair k1=generateRandom();
//        PublicKey=(ECPublicKey)k1.getPublic();
//        PrivateKey=(ECPrivateKey)k1.getPrivate();
//
//    }
//    private static void testDKeyGen() {
//
//        DKeyGen dKeyGen = new DKeyGen(msk, w, TWO.pow(securityParameter));
//        Triplet<ArrayList<BigInteger>, ECPoint, ECPoint> fpkTriplet =
//                dKeyGen.getfpk(g, g.getCurve());;
//    }
//    private static void testEncrypt() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
//
//        EllipticCurve curve =PrivateKey.getParams().getCurve();
//
//        System.out.println(PublicKey);
//
//        BigInteger messageBigInteger=nextRandomBigInteger(40);
//
//        BigInteger label=nextRandomBigInteger(securityParameter);
//
//        ECPoint p1=PublicKey.getW();
//        String output = "";
//        for(int i=0;i<w.size();i++) {
//            Encryption tEncryption=new Encryption(messageBigInteger,label,msk.get(i).getKey(),msk.get(i).getValue());
//            ECPoint point=tEncryption.getCipherText(p1, curve);
//            output=output+("The x coordinate of cipherText "+(i+1)+" is: "+"\n"+point.getAffineX()+"\n");
//            output=output+("The y coordinate of cipherText "+(i+1)+" is: "+"\n"+point.getAffineY()+"\n");
//
//        }
//        agroa_textView_output.setText(output);
//
//    }
//    public static BigInteger nextRandomBigInteger(Integer securityParameter) {
//        BigInteger n=TWO.pow(securityParameter);
//        Random rand = new Random();
//        BigInteger result = new BigInteger(n.bitLength(), rand);
//        while( result.compareTo(n) >= 0 ) {
//            result = new BigInteger(n.bitLength(), rand);
//        }
//        return result;
//    }
//    public static KeyPair generateRandom() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
////        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
//        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256r1");
//        keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        return keyPair;
//    }
//
//    //These two functions below are used to print the privateKey
//    private static String getPrivateKeyAsHex(java.security.PrivateKey privateKey) {
//
//        ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;
//        byte[] privateKeyBytes = new byte[24];
//        writeToStream(privateKeyBytes, 0, ecPrivateKey.getS(), 24);
//
//        return Hex.toHexString(privateKeyBytes);
//    }
//    private static void writeToStream(byte[] stream, int start, BigInteger value, int size) {
//        byte[] data = value.toByteArray();
//        int length = Math.min(size, data.length);
//        int writeStart = start + size - length;
//        int readStart = data.length - length;
//        System.arraycopy(data, readStart, stream, writeStart, length);
//    }
//
//}