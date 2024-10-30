package file_loader;
import java.util.*;
import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.stream.Stream;

public class file_loader {

	
	/*
		AUTOR: Fernando Araujo Barros
		DATA DE CRIAÇÃO: 29/10/2024
		LinkedIn: https://www.linkedin.com/in/fernando-barros-21830826a/
		Github: https://github.com/Fer-Barros
	*/
	
	
	
	
	
	
	private static String check_class(String className) throws IOException {
        // Usa o file system JRT para acessar módulos no Java 9+
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));

        // Caminho para o módulo java.base
        Path basePath = fs.getPath("/modules/java.base");

        try (Stream<Path> paths = Files.walk(basePath)) {
            Optional<String> foundClass = paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".class"))
                .map(path -> path.toString()
                        .replace("/modules/java.base/", "")
                        .replace("/", ".")
                        .replace("\\", ".")
                        .replace(".class", ""))
                .filter(fullClassName -> fullClassName.endsWith("." + className))
                .findFirst();
            
            return foundClass.map(fullClassName -> "import " + fullClassName + ";").orElse(null);
        }
    } 
	
	
	public static String template(String usr_package, String filename, String[] pred) {
		
		// Variável do template final
		String template = String.format("package %s; \n", usr_package);
		
		// Definindo base do template
		String base_template = String.format("""
				
				public class %s {
					
					public static void main (String[] args){
						// Bem vindo ao arquivo gerado com file_loader
						
					}
				}
				""", filename);
		
		if (pred != null && pred.length > 0 ) {
			template = String.format("package %s; \n", usr_package);
			for (String item_pred : pred) {
				String found_import = "";
				try {
					found_import = check_class(item_pred);	
				}catch(IOException e) {
					System.out.println("Erro ao buscar classe na base do JAVA");
					e.printStackTrace();
				}
				
				if(found_import.length() > 0 && found_import != null) {
					template += found_import + "\n";
				}
			}
					
		}
		
		
		template += base_template;	
		return template;
	}
	
	
	public static void main(String[] args){
		int opt = 0, file_quant = 0, inner_opt = 0;
		String usr_package = "file_loader", usr_file_path = "src/"+ usr_package +"/", file_name = "";
		String class_pred = "", file_pred = "";
		Map<Integer, String[]> pred = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		
		do {
			// Menu de opções do usuário
			System.out.println("""
					Escolha uma opção:
					1 - Criar arquivos
					2 - Executar arquivo
					3 - Desligar
					""");
			opt = Integer.parseInt(sc.nextLine());
			
			
			// Verificando opção selecionada
			switch(opt) {
				// Case para criação de arquivos
				case 1:
					System.out.println("Informe a quantidade de arquivos que serão criados");
					file_quant = Integer.parseInt(sc.nextLine());
					
					do {
						
						System.out.println("""
								Deseja predefinir classes que serão utilizadas?
								(Ex.: Scanner, IOException)
								0 - Não
								1 - Sim
								""");
						inner_opt = Integer.parseInt(sc.nextLine());
						if (inner_opt != 0) {

							// Recebe uma string inteira com vírgulas
							System.out.println("Digite o nome da classe a ser utilizada (separe por vírgula)");
							class_pred = sc.nextLine();
							
							// Remove os espaços em branco e separa por vírgula
							class_pred = class_pred.replaceAll("\\s", "");
							String[] class_pred_arr = class_pred.split(",");
							
									
							// Recebe uma string inteira com vírgulas
							System.out.println("""
									Aplique as predefinições por n° de arquivo (separe por vírgula)
									(Ex.: 1, 2, 3...)
									---------------------------------
									0 para definir a todos os arquivos
									
									""");
							file_pred = sc.nextLine();
							
							// Remove os espaços em branco e separa por vírgula
							file_pred = file_pred.replaceAll("\\s", "");
							String[] file_pred_arr = file_pred.split(",");							
							
							// Verifica se a opção "Todos" foi digitada
							if (Arrays.stream(file_pred_arr).anyMatch("0"::equals)) {
								for (int i = 1; i <= file_quant; i++) {
									pred.put(i, class_pred_arr);
								}
							}else {
								for (String file_n : file_pred_arr) {
									pred.put(Integer.parseInt(file_n), class_pred_arr);
								}	
							}	
							
							

						}else {
							System.out.println("\nRetornando para criação de arquivos...\n");
						}
						
						
					}while(inner_opt != 0);

					
					// Fluxo de criação de arquivo 
				
					try {
						for (int i = 1; i <= file_quant; i++) {
						
							// Criando arquivos
							file_name = "ex"+i+".java";
							File arq = new File(usr_file_path+file_name);
						
							if(arq.createNewFile()) {
								System.out.println("Arquivo criado com sucesso: " + file_name);
								
								// Tenta escrever dentro do arquivo criado
								try {
									// Removendo extensão do nome do arquivo
									int extension_index = file_name.indexOf('.');
									String treated_file_name = file_name.substring(0, extension_index);
									
									// Escrevendo dentro do arquivo o conteúdo de template
									FileWriter arq_escreve = new FileWriter(usr_file_path+file_name);
									
									// Se houver predefinições, escrever arquivo com cada uma
									if (pred.get(i) != null) {
										arq_escreve.write(template(usr_package, treated_file_name, pred.get(i)));
									}else {
										arq_escreve.write(template(usr_package, treated_file_name, null));										
									}
									
									// Fechando escrita de arquivo
									arq_escreve.close();
									
								} catch(IOException e) {
									System.out.println("Houve um erro ao escrever no arquivo!");
								}
							}else {
								System.out.println("Arquivo já existe: " + file_name);
							}
							
							
						}
						System.out.println("");
					}catch(IOException e) {
						System.out.println("Houve um erro na criação dos arquivos!");
						e.printStackTrace();
					}
					
				break;
				// Case para execução dos arquivos criados
				case 2:
					String file_n_id = "";
					System.out.println("Digite o ID numérico do arquivo (n° Exercício)");
					file_n_id = sc.nextLine();
					try {
			            // Define o comando para abrir um novo terminal e executar o programa Java
			            ProcessBuilder processBuilder = new ProcessBuilder();

			            // Ajusta o diretório de trabalho
			            processBuilder.directory(new File(usr_file_path));

			            if (System.getProperty("os.name").toLowerCase().contains("win")) {
			                // Windows
			                processBuilder.command("cmd.exe", "/c", "start", "cmd.exe", "/k", "java " + "ex" + file_n_id + ".java");
			            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			                // MacOS
			                processBuilder.command("open", "-a", "Terminal.app", "--args", "java " + "ex" + file_n_id + ".java" + "; read");
			            } else {
			                // Linux
			                processBuilder.command("x-terminal-emulator", "-e", "sh", "-c", "java " + "ex" + file_n_id + ".java" + "; read");
			            }

			            // Inicia o processo
			            processBuilder.start();
			            System.out.println("Novo terminal aberto e " + "ex" + file_n_id + ".java, rodando");

			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				break;
				case 3:
					System.out.println("Desligando...");
				break;
				default:
					System.out.println("Opção inválida, tente novamente! \n");
				break;
			}
		}while(opt != 3);
		
		
		sc.close();
	}

}
