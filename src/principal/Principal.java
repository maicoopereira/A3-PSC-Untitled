package principal;
import model.Produto;
import DAO.EstoqueDAO;
import java.time.LocalDate;
import view.TelaPrincipal;

public class Principal {

    public static void main(String[] args) {
        
        //Produto p1 = new Produto(12, "Arroz Tio João 1kg", "arroz branco tipo 1", "alimentos", "7,49", "10-12-2025");
Produto p1 = new Produto(12, "Arroz Tio João 1kg", "arroz branco tipo 1", "alimentos", "7,49", "10-12-2025");
EstoqueDAO.InsertProdutoBD(p1);

Produto p2 = new Produto(8, "Feijão Carioca Kicaldo 1kg", "feijão carioca selecionado", "alimentos", "6,29", "15-11-2025");
EstoqueDAO.InsertProdutoBD(p2);

Produto p3 = new Produto(20, "Leite Integral Piracanjuba", "leite integral 1L", "laticínios", "4,89", "05-02-2026");
EstoqueDAO.InsertProdutoBD(p3);

Produto p4 = new Produto(15, "Café Melitta 500g", "café torrado e moído", "alimentos", "12,50", "22-04-2026");
EstoqueDAO.InsertProdutoBD(p4);

Produto p5 = new Produto(9, "Açúcar União 1kg", "açúcar refinado", "alimentos", "3,99", "09-10-2025");
EstoqueDAO.InsertProdutoBD(p5);

Produto p6 = new Produto(6, "Refrigerante Coca-Cola 2L", "refrigerante tradicional", "bebidas", "8,49", "18-03-2026");
EstoqueDAO.InsertProdutoBD(p6);

Produto p7 = new Produto(4, "Suco Del Valle Uva", "suco de uva 1L", "bebidas", "6,79", "12-06-2026");
EstoqueDAO.InsertProdutoBD(p7);

Produto p8 = new Produto(10, "Água Mineral Crystal 1,5L", "água mineral sem gás", "bebidas", "2,49", "03-09-2026");
EstoqueDAO.InsertProdutoBD(p8);

Produto p9 = new Produto(7, "Cerveja Amstel", "cerveja pilsner lata 350ml", "bebidas alcoólicas", "4,29", "22-08-2025");
EstoqueDAO.InsertProdutoBD(p9);

Produto p10 = new Produto(11, "Cerveja Budweiser", "long neck 330ml", "bebidas alcoólicas", "5,49", "14-09-2025");
EstoqueDAO.InsertProdutoBD(p10);

Produto p11 = new Produto(13, "Macarrão Renata Espaguete", "massa tradicional 500g", "alimentos", "3,29", "30-01-2026");
EstoqueDAO.InsertProdutoBD(p11);

Produto p12 = new Produto(5, "Molho de Tomate Pomarola", "molho tradicional 340g", "alimentos", "2,99", "07-12-2025");
EstoqueDAO.InsertProdutoBD(p12);

Produto p13 = new Produto(14, "Óleo de Soja Soya 900ml", "óleo de soja refinado", "alimentos", "5,59", "19-05-2026");
EstoqueDAO.InsertProdutoBD(p13);

Produto p14 = new Produto(6, "Sal Cisne 1kg", "sal refinado", "alimentos", "2,29", "11-11-2026");
EstoqueDAO.InsertProdutoBD(p14);

Produto p15 = new Produto(8, "Farinha de Trigo Anaconda 1kg", "farinha tradicional", "alimentos", "4,79", "27-03-2026");
EstoqueDAO.InsertProdutoBD(p15);

Produto p16 = new Produto(9, "Detergente Ypê Neutro", "detergente líquido 500ml", "limpeza", "1,99", "01-01-2027");
EstoqueDAO.InsertProdutoBD(p16);

Produto p17 = new Produto(4, "Sabão em Pó Omo 1,6kg", "sabão para roupas", "limpeza", "18,90", "14-04-2027");
EstoqueDAO.InsertProdutoBD(p17);

Produto p18 = new Produto(7, "Desinfetante Veja Citrus 1L", "desinfetante multiuso", "limpeza", "7,49", "21-08-2027");
EstoqueDAO.InsertProdutoBD(p18);

Produto p19 = new Produto(12, "Esponja Scotch-Brite", "esponja multiuso", "limpeza", "3,59", "28-02-2028");
EstoqueDAO.InsertProdutoBD(p19);

Produto p20 = new Produto(10, "Amaciante Downy 1L", "amaciante concentrado", "limpeza", "11,90", "09-09-2027");
EstoqueDAO.InsertProdutoBD(p20);

Produto p21 = new Produto(6, "Shampoo Dove 400ml", "shampoo nutritivo", "higiene pessoal", "12,49", "18-03-2027");
EstoqueDAO.InsertProdutoBD(p21);

Produto p22 = new Produto(5, "Sabonete Dove 90g", "sabonete hidratante", "higiene pessoal", "3,29", "15-12-2026");
EstoqueDAO.InsertProdutoBD(p22);

Produto p23 = new Produto(7, "Creme Dental Colgate Total 90g", "creme dental proteção total", "higiene pessoal", "5,19", "03-07-2027");
EstoqueDAO.InsertProdutoBD(p23);

Produto p24 = new Produto(4, "Desodorante Rexona Men Aerosol", "desodorante antitranspirante", "higiene pessoal", "11,49", "19-05-2027");
EstoqueDAO.InsertProdutoBD(p24);

Produto p25 = new Produto(9, "Papel Higiênico Neve 12 rolos", "folha dupla", "higiene pessoal", "18,99", "12-10-2028");
EstoqueDAO.InsertProdutoBD(p25);

Produto p26 = new Produto(3, "Queijo Mussarela Sadia 500g", "queijo fatiado", "laticínios", "19,90", "21-02-2025");
EstoqueDAO.InsertProdutoBD(p26);

Produto p27 = new Produto(6, "Presunto Seara 500g", "presunto cozido fatiado", "laticínios", "14,49", "13-03-2025");
EstoqueDAO.InsertProdutoBD(p27);

Produto p28 = new Produto(8, "Iogurte Nestlé Morango", "bandeja com 6 unidades", "laticínios", "7,89", "29-01-2025");
EstoqueDAO.InsertProdutoBD(p28);

Produto p29 = new Produto(10, "Manteiga Aviação 200g", "manteiga tradicional", "laticínios", "12,99", "04-05-2025");
EstoqueDAO.InsertProdutoBD(p29);

Produto p30 = new Produto(5, "Requeijão Catupiry 200g", "cremoso tradicional", "laticínios", "9,99", "17-04-2025");
EstoqueDAO.InsertProdutoBD(p30);


        System.out.println(EstoqueDAO.maiorID());
        //System.out.println(p1.getIdProdutos());
        
        //EstoqueDAO.InsertProdutoBD(p1);

        System.out.println(EstoqueDAO.gerarLista().toString());
        
//        EstoqueDAO.RemoveProdutoBD(
//            1,2,3,4,5,6,7,8,9,10,
//            11,12,13,14,15,16,17,18,19,20,
//            21,22,23,24,25,26,27,28,29,30,
//            31,32,33,34,35,36,37,38,39,40,
//            41,42,43,44,45,46,47,48,49,50,
//            51,52,53,54,55,56,57,58,59,60,
//            61,62,63,64,65,66,67,68,69,70,
//            71,72,73,74,75,76,77,78,79,80,
//            81,82,83,84,85,86,87,88,89,90,
//            91,92,93,94,95,96,97,98,99,100
//        );

        System.out.println(EstoqueDAO.gerarLista());
        
        
        //TelaPrincipal objetotela = new TelaPrincipal();
        //objetotela.setVisible(true);
    }
    
    
}
