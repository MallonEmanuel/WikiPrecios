package unpsjb.wikiprecios;

/**
 * Created by emanuel on 20/09/17.
 */
public class RoutesURL {

        // Servicios POST

//      http://localhost/wikiprecios/api/usuario/login/?mail=%22ema3@gmail.com%22&password=%22ema%22
//      {"noUser":1}
//      {"id":"14","name":"emanuel","surname":"Mallon","mail":"ema3@gmail.com","password":"af87d9ecd674dc032707d7166ef4439f2d50b194e3435ac6c6975e9754dd6357c5e562310ef7f698f0080f67d14e37a57261ea7aa9dd534d9f7612445c74c150hGDue6E0SHfikV3ZIwN8NFQDh3aaPaksnNEhissnl4U=","qualification":"1","accumulated":"0","active_account":"1"}
        public static String URL_LOGIN = "/wikiprecios/api/usuario/login";

//      http://localhost/wikiprecios/api/usuario/registrar/?password=%22ema%22&mail=%22ema@gmail.com%22&name=%22emanuel%22&surname=%22Mallon%22
//      {"mensaje":"El mail ingresado ya fue utilizado en otra cuenta","registrado":false}
//      {"registrado":true,"mensaje":"Registrado con exito"}
        public static String URL_REGISTER = "/wikiprecios/api/usuario/registrar";


        // Servicios GET
//        public static String URL_COMMERCES_SUG = "http://precios.draggon.com.ar/wikiPrecios/comercios/";
//        public static String URL_COMMERCES_FAVOURITES = "http://precios.draggon.com.ar/wikiPrecios/comerciosFavoritos/";
//        public static String URL_COMMERCES_FAVOURITES_SAVE = "http://precios.draggon.com.ar/wikiPrecios/favorito/";
//        public static String URL_PRICES_SUG = "http://precios.draggon.com.ar/wikiPrecios/precioSugerido/";
//        public static String URL_PRICES_SAVE = "http://precios.draggon.com.ar/wikiPrecios/precio/";
//
//
//        public static String URL_GET_R = "http://precios.draggon.com.ar/wikiPrecios/obtenerRubros/";
//        public static String URL_GET_C = "http://precios.draggon.com.ar/wikiPrecios/obtenerCategorias/";
}
