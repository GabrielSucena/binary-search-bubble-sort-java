import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
	
	public static class Projeto {
		String CPF, link;
		Double nota1, nota2, nota3, media;
	}

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Quantos projetos serão cadastrados? ");
		int N = sc.nextInt();
		while(N < 2 || N > 8) {
			System.out.print("Quantidade Inválida! (Mínimo 2 projetos - Máximo 8): ");
			N = sc.nextInt();
		}
		
		Projeto listaDeProjetos[] = new Projeto[N];
		
		for(int i = 0; i < N; i++) {
			Projeto projeto = new Projeto();
			System.out.println();
			System.out.println("Produto #" + (i + 1) + ":");
			System.out.print("CPF: ");
			String CPF = sc.next();
			projeto.CPF = CPFtoINT(CPF);
			for(int tamanhoVetorAtual = 0; tamanhoVetorAtual < i; tamanhoVetorAtual++) {
				while(projeto.CPF.equals(listaDeProjetos[tamanhoVetorAtual].CPF)) {
					System.out.print("CPF já cadastrado. DIGITE NOVAMENTE: ");
					CPF = sc.next();
					projeto.CPF = CPFtoINT(CPF);
				}
			}
			System.out.print("Link: ");
			projeto.link = sc.next();
			listaDeProjetos[i] = projeto;
		}
		
		// Ordenação do Vetor pelo CPF utilizando bubbleSort
		bubbleSort(N, listaDeProjetos);
		
		System.out.println();
		System.out.println("----------------Produtos para serem avaliados!----------------");
		for(int contador = 0; contador < N; contador++) {
			System.out.println();
			System.out.println("CPF: " + INTtoCPF(listaDeProjetos[contador].CPF) + "\nLink Prévia: " + listaDeProjetos[contador].link);
			System.out.println("------------------------------------------------------");
		}
		
		System.out.println();
		System.out.print("Cadastro de Notas");
		for (int i=0; i<N; i++) {
			System.out.println();
			System.out.println("----- Projeto: " + INTtoCPF(listaDeProjetos[i].CPF) + " -----");
			System.out.println();
			System.out.print("Nota primeiro Avaliador: ");
			double nota1 = sc.nextDouble();
			while(nota1 < 0 || nota1 > 10) {
				System.out.print("Nota digitada invalida! (0-10): ");
				nota1 = sc.nextDouble();
			}
			listaDeProjetos[i].nota1 = nota1;
				
			System.out.print("Nota segundo Avaliador: ");
			double nota2 = sc.nextDouble();
			while(nota2 < 0 || nota2 > 10) {
				System.out.print("Nota digitada invalida! (0-10): ");
				nota2 = sc.nextDouble();
			}
			listaDeProjetos[i].nota2 = nota2;
				
			System.out.print("Nota terceiro Avaliador: ");
			double nota3 = sc.nextDouble();
			while(nota3 < 0 || nota3 > 10) {
				System.out.print("Nota digitada invalida! (0-10): ");
				nota3 = sc.nextDouble();
			}
			listaDeProjetos[i].nota3 = nota3;
			calculoMedia(nota1, nota2, nota3, listaDeProjetos, i);
			
		}
		
		System.out.println();
		System.out.println("---------------------------Dados Produtos---------------------------");
		System.out.println();
		for (int i=0; i<N; i++) {
			System.out.println("----- Projeto: " + (i+1) + " -----");
			System.out.println("CPF: " + INTtoCPF(listaDeProjetos[i].CPF));
			System.out.println("Link: " + listaDeProjetos[i].CPF);
			System.out.println("Nota 1: " + listaDeProjetos[i].nota1);
			System.out.println("Nota 2: " + listaDeProjetos[i].nota2);
			System.out.println("Nota 3: " + listaDeProjetos[i].nota3);
			System.out.printf("Média: %.2f\n", listaDeProjetos[i].media);
			System.out.println();
		}
		
		System.out.println("-----------------Produto (s) com Melhor Média-----------------");
		if(maioresMedias(listaDeProjetos, N).size() == 1) {
			System.out.println();
			System.out.println("-----PRODUTO VENCEDOR-----");
			System.out.println();
			for(Projeto p : maioresMedias(listaDeProjetos, N)) {
				System.out.println("CPF: " + INTtoCPF(p.CPF));
				System.out.println("Link: " + p.link);
				System.out.println();
			}
		}else {
			System.out.println("-----VOTO DE MINERVA-----");
			for(Projeto p : maioresMedias(listaDeProjetos, N)) {
				System.out.println("CPF: " + INTtoCPF(p.CPF));
				System.out.println("Link: " + p.link);
				System.out.println();
			}
			System.out.print("Escolha um dos Produtos acima pelo CPF: ");
			String escolha = sc.next();
			System.out.println();
			System.out.println("-----PRODUTO VENCEDOR-----");
			System.out.println();
			for(Projeto p : maioresMedias(listaDeProjetos, N)) {
				if(p.CPF.equals(CPFtoINT(escolha))) {
					System.out.println("CPF: " + INTtoCPF(p.CPF));
					System.out.println("Link: " + p.link);
					System.out.println();
				}
			}	
		}
		
		//Busca Binária
		System.out.print("Deseja Buscar por um produto? (s/n) ");
		char buscaResposta = sc.next().charAt(0);
		
		while(buscaResposta == 's') {
			System.out.print("Digite um CPF para buscar: ");
			String cpfBusca = sc.next();
			cpfBusca = CPFtoINT(cpfBusca);
			System.out.println(buscaBinaria(listaDeProjetos, cpfBusca));
			System.out.println();
			System.out.print("Deseja fazer uma nova busca? (s/n) ");
			buscaResposta = sc.next().charAt(0);
		}
		
		sc.close();

	}
	
	// FUNÇÕES AUXILIARES
	public static void calculoMedia(double nota1, double nota2, double nota3, Projeto listaDeProjetos[], int i) {
		double media = (nota1 + nota2 + nota3) / 3;
		listaDeProjetos[i].media = media;
	}
	
	public static List<Projeto> maioresMedias(Projeto listaDeProjetos[], int N){
		Projeto procuraMaior = listaDeProjetos[0];
		double maior = procuraMaior.media;
		for (int i=0; i<N; i++) {
			Projeto p = listaDeProjetos[i];
			double maiorTeste = p.media;
			if(maiorTeste > maior) {
				maior = maiorTeste;
			}
		}
		List<Projeto> maioresNotas = new ArrayList<>();
		for (int i=0; i<N; i++) {
			Projeto p = listaDeProjetos[i];
			double maiorTeste = p.media;
			if(maiorTeste == maior) {
				maioresNotas.add(p);
			}
		}
		
		return maioresNotas;
		
	}
	
	public static void bubbleSort(int N, Projeto listaDeProjetos[]) {
		String aux;
		String auxLink;
		
		for(int j = 0; j < N; j++) {
			for(int i = 0; i < (N - 1); i++) {
				if(Long.parseLong(listaDeProjetos[i].CPF) > Long.parseLong(listaDeProjetos[i+1].CPF)) {
					aux = listaDeProjetos[i].CPF;
					auxLink = listaDeProjetos[i].link;
					
					listaDeProjetos[i].CPF = listaDeProjetos[i+1].CPF;
					listaDeProjetos[i].link = listaDeProjetos[i+1].link;
					
					listaDeProjetos[i+1].CPF = aux;
					listaDeProjetos[i+1].link = auxLink;
				}
			}
		}
	}
	
	public static String buscaBinaria(Projeto listaDeProjetos[], String cpfBusca) {
		boolean achou = false;
		int index = 0;
		int inicio = 0;
		int fim = listaDeProjetos.length - 1;
		int meio;
		
		while(inicio <= fim) {
			meio = (int) ((inicio + fim) /2);
			if(listaDeProjetos[meio].CPF.equals(cpfBusca)) {
				achou = true;
				index = meio;
				break;
			}else if(Long.parseLong(listaDeProjetos[meio].CPF) < Long.parseLong(cpfBusca)) {
				inicio = meio + 1;
			}else {
				fim = meio - 1;
			}
		}
			
		if(achou == true) {
			System.out.println();
			return "Produto na posição: " + index + " do vetor"
					+ "\nDados do Produto: "
					+ "\nCPF: " + INTtoCPF(listaDeProjetos[index].CPF)
					+ "\nLink: " + listaDeProjetos[index].link;
		}else {
			System.out.println();
			return "Produto inexistente!";
		}
	}
	
	public static String CPFtoINT (String CPF) {
		CPF = CPF.replaceAll("\\.", "");
		CPF = CPF.replaceAll("-", "");
		return CPF;
	}
	
	public static String INTtoCPF (String CPF) {
		CPF = CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
		return CPF;
	}
	
}