package br.com.cast.turmaformacao.inventorymanager.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Administrador on 25/09/2015.
 */
public class Product implements Parcelable {
    @JsonIgnore
    private Long id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("image")
    private String image;
    @JsonProperty("name")
    private String nome;
    @JsonProperty("description")
    private String descricao;
    @JsonProperty("stock")
    private Long quantidade;
    @JsonProperty("minimunStock")
    private Long quantidadeMin;
    @JsonProperty("unitaryValue")
    private Double valorUnitario;
    @JsonProperty("id")
    private Long webId;
    @JsonProperty("date")
    private Long date;


    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.valorUnitario, valorUnitario) != 0) return false;
        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (image != product.image) return false;
        if (nome != null ? !nome.equals(product.nome) : product.nome != null) return false;
        if (descricao != null ? !descricao.equals(product.descricao) : product.descricao != null)
            return false;
        if (quantidade != null ? !quantidade.equals(product.quantidade) : product.quantidade != null)
            return false;
        return !(quantidadeMin != null ? !quantidadeMin.equals(product.quantidadeMin) : product.quantidadeMin != null);

    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        result = 31 * result + (quantidadeMin != null ? quantidadeMin.hashCode() : 0);
        temp = Double.doubleToLongBits(valorUnitario);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", image=" + image +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", quantidadeMin=" + quantidadeMin +
                ", valorUnitario=" + valorUnitario +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Product() {
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Product(String descricao, String nome, String image, Long quantidade, Long quantidadeMin, Double valorUnitario, Long id) {
        this.descricao = descricao;
        this.nome = nome;
        this.image = image;
        this.quantidade = quantidade;
        this.quantidadeMin = quantidadeMin;
        this.valorUnitario = valorUnitario;
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return image;
    }

    public void setImagem(String image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Long getQuantidadeMin() {
        return quantidadeMin;
    }

    public void setQuantidadeMin(Long quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id == null ? -1 : this.id);
        dest.writeString(this.image == null ? "" : this.image);
        dest.writeString(this.nome == null ? "" : this.nome);
        dest.writeString(this.descricao == null ? "" : this.descricao);
        dest.writeValue(this.quantidade == null ? -1 : this.quantidade);
        dest.writeValue(this.quantidadeMin == null ? -1 : this.quantidadeMin);
        dest.writeDouble(this.valorUnitario == null ? -1 : this.valorUnitario);
        dest.writeValue(this.webId == null ? -1 : this.webId);
        dest.writeValue(this.date == null ? -1 : this.date);
    }

    protected Product(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.image = in.readString();
        this.nome = in.readString();
        this.descricao = in.readString();
        this.quantidade = (Long) in.readValue(Long.class.getClassLoader());
        this.quantidadeMin = (Long) in.readValue(Long.class.getClassLoader());
        this.valorUnitario = (Double) in.readValue(Double.class.getClassLoader());
        this.webId = (Long) in.readValue(Long.class.getClassLoader());
        this.date = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
