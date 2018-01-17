package unpsjb.wikiprecios.config;

/**
 * Wikiprecios es un cliente que se conecta a un servidor web, esta clase contiene las rutas para realizar las consultas Http.
 * Ver la seccion de Api rest para mas información.
 */
public class Routes {

//      http://localhost/wikiprecios/api/usuario/login/?mail=%22ema3@gmail.com%22&password=%22ema%22
//      {"noUser":1}
//      {"id":"14","name":"emanuel","surname":"Mallon","mail":"ema3@gmail.com","password":"af87d9ecd674dc032707d7166ef4439f2d50b194e3435ac6c6975e9754dd6357c5e562310ef7f698f0080f67d14e37a57261ea7aa9dd534d9f7612445c74c150hGDue6E0SHfikV3ZIwN8NFQDh3aaPaksnNEhissnl4U=","qualification":"1","accumulated":"0","active_account":"1"}
        /** Ruta que permite al usuario logearse a wikiprecios**/
        public static String URL_LOGIN = "/wikiprecios/api/usuario/login";

        /** Ruta que permite al usuario logearse con una cuenta de Facebook **/
        public static String URL_FACEBOOK_LOGIN = "/wikiprecios/api/usuario/login_facebook";

//      http://localhost/wikiprecios/api/usuario/registrar/?password=%22ema%22&mail=%22ema@gmail.com%22&name=%22emanuel%22&surname=%22Mallon%22
//      {"mensaje":"El mail ingresado ya fue utilizado en otra cuenta","registrado":false}
//      {"registrado":true,"mensaje":"Registrado con exito"}
        /** Ruta que permite al usuario registrarse en el sistema **/
        public static String URL_REGISTER = "/wikiprecios/api/usuario/registrar";


        // Servicios GET
//        http://localhost/wikiprecios/api/comercios/cercanos/?latitud=-42.785849&longitud=-65.005789&usuario="emanuelbalcazar13@gmail.com"
        /** Ruta que le brinda al usuario los comercios mas cercanos **/
        public static String URL_NEARBY_COMMERCES = "/wikiprecios/api/comercios/cercanos";
//        public static String URL_COMMERCES_FAVOURITES = "http://precios.draggon.com.ar/wikiPrecios/comerciosFavoritos/";
//        public static String URL_COMMERCES_FAVOURITES_SAVE = "http://precios.draggon.com.ar/wikiPrecios/favorito/";

//    http://localhost/wikiprecios/api/precio/sugerido/?commerce=1&product="7791615000426"
        /** Ruta que respecto a un producto, en un comercio, informa los precios ingresados anteriormente por otros usuarios.**/
        public static String URL_SUGGESTED_PRICE = "/wikiprecios/api/precio/sugerido";
//        public static String URL_PRICES_SAVE = "http://precios.draggon.com.ar/wikiPrecios/precio/";
//
        /** Ruta que permite guardar el precio ingresado por el usuario y devuelve la información del producto buscado**/
        public static String URL_SAVE_PRICE = "/wikiprecios/api/precio/registrar";
//
//        [{"id":"1","letter":"CAR","name":"carnes"},{"id":"2","letter":"VER","name":"verduras"},{"id":"3","letter":"PAN","name":"panaderia"},{"id":"4","letter":"FRU","name":"frutas"}]
        /** Ruta que devuelve los distintos rubros **/
        public static String URL_GET_RUBRO = "/wikiprecios/api/rubros";

//    http://localhost/wikiprecios/api/rubro/categorias/?item=1
//    [{"id":"1","item_id":"1","category":"peceto","special_product_code":"CAR1","unit":"KG"}]
        /** Ruta que informa las distintas categorias de un rubro seleccionado**/
        public static String URL_GET_CATEGORY = "/wikiprecios/api/rubro/categorias";

//      http://localhost/wikiprecios/api/favorito/registrar/?user="emanuelbalcazar13@gmail.com"&commerce=1,2,3
        /** Ruta que permite guardar los comercios favoritos del usuario**/
        public static String ULR_SAVE_FAVOURITES_COMMERCES = "/wikiprecios/api/favorito/registrar";

//        http://localhost/wikiprecios/api/comercio/registrar/?name="La anonima"&address
//        ="Jujuy 1330"&latitude=-42.7805146&longitude=-65.0447228&
//        city="Puerto Madryn"&province="Chubut"&country="Argentina"
        /** Ruta que permite registrar un comercio **/
        public static String URL_SAVE_COMMERCE = "/wikiprecios/api/comercio/registrar";

}
