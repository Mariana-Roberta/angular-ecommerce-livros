// src/app/models/user.model.ts

export class User {
  id: Number;
  cpf: String;
  email: String;
  telefone: String;
  endereco: String;
  senha: String;
  tipoUsuario: String;
  // outros campos relevantes do usuário

  constructor(
    id: Number,
    cpf: String,
    email: String,
    telefone: String,
    endereco: String,
    senha: String,
    tipoUsuario: String,
    // outros parâmetros relevantes
  ) {
    this.id = id;
    this.cpf = cpf;
    this.email = email;
    this.telefone = telefone;
    this.endereco = endereco;
    this.senha = senha;
    this.tipoUsuario = tipoUsuario;
    // inicialize outros campos
  }
}
