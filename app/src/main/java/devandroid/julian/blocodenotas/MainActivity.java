package devandroid.julian.blocodenotas;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private AnotacaoPreferencias preferencias;
    private EditText editAnotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.yellow));
        }

        FloatingActionButton fbSalvar = findViewById(R.id.fb_salvar);
        editAnotacao = findViewById(R.id.editAnotacoes);
        preferencias = new AnotacaoPreferencias(getApplicationContext());

        fbSalvar.setOnClickListener(view -> {
            String textoRecuperado = editAnotacao.getText().toString();

            if (textoRecuperado.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Digite uma anotação!", Toast.LENGTH_SHORT).show();
            } else {
                preferencias.salvarAnotacao(textoRecuperado);
                Toast.makeText(getApplicationContext(), "Anotação salva com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        String anotacao = preferencias.recuperarAnotacao();

        if (!anotacao.isEmpty()) {
            editAnotacao.setText(anotacao);
        }
    }
}