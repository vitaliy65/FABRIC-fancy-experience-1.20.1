package com.vurbin.fancyexperience.Screen;

import com.vurbin.fancyexperience.Player.PlayerStats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Math;

import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;

@Environment(EnvType.CLIENT)
public class UpgradeScreen extends Screen {
    private final PlayerStats playerStats;
    private static final String BACKGROUND_TEXTURE = "textures/gui/background.png";
    private static final String BACKGROUND_PLAYER_TEXTURE = "textures/gui/background_player.png";
    private static final String mod_id = MOD_ID;

    protected UpgradeScreen(PlayerStats playerStats) {
        super(Text.of("Upgrade Menu"));
        this.playerStats = playerStats;
    }

    @Override
    protected void init() {
        renderCustomButtons();
    }


    // For versions 1.20 and after
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Identifier backgroundTexture = Identifier.of(mod_id, BACKGROUND_TEXTURE);
        Identifier backgroundPlayerTexture = Identifier.of(mod_id, BACKGROUND_PLAYER_TEXTURE);

        renderBackground(context, 480, 270, (int)(width / 4.26f), (int)(height / 12f), backgroundTexture);
        renderBackground(context, 135, 270, (int)(width / 42f), (int)(height / 12f), backgroundPlayerTexture);

        // Рендерим игрока, чтобы он следил за курсором
        renderPlayerModelWithDrawContext(context, (int)(width / 8f), (int)(height / 1.5f), 80, mouseX, mouseY, MinecraftClient.getInstance().player);

        renderCustomText(context);

        super.render(context, mouseX, mouseY, delta);
    }

    // Метод для отрисовки текста с разным размером
    private void renderCustomText(DrawContext context) {
        int xScale = (int)(width/3.76f);
        int xBaseOffset = (int)(width/6f);
        int yScale = (int)(height/7.2f);
        int yBaseOffset = (int)(height/18.5f);
        float screenScale = getScaleForScreen(width, height);
        int positionR = xScale + xBaseOffset * 2 + (width/10);

        int attributePositionWidth = (int)(positionR + width / 6.5 * screenScale);

        renderTextWithScale ( context ,  "Уровень:",         xScale + (width/12),  yScale + yBaseOffset,     screenScale * 1.2f, false);
        renderTextWithScale ( context ,  playerStats.getExp () + "/" +playerStats.getExpForNextLevel (),  xScale + xBaseOffset, yScale + yBaseOffset * 4, screenScale * 1.9f, true);
        renderTextWithScale ( context ,  "следующий уровень",xScale + xBaseOffset, yScale + yBaseOffset * 5, screenScale * 0.6f, true);

        renderTextWithScale ( context ,  "Атребуты:",   positionR , yScale + yBaseOffset ,          screenScale * 1.2f , false);
        renderTextWithScale ( context ,  "Живучесть",   positionR , yScale + yBaseOffset*2,         screenScale * 0.9f , false);
        renderTextWithScale ( context ,  "Выносливость",positionR , yScale + (int)(yBaseOffset*2.8),screenScale * 0.9f , false);
        renderTextWithScale ( context ,  "Сила",        positionR , yScale + (int)(yBaseOffset*3.6),screenScale * 0.9f , false);
        renderTextWithScale ( context ,  "Ловкость",    positionR , yScale + (int)(yBaseOffset*4.4),screenScale * 0.9f , false);
        renderTextWithScale ( context ,  "Удача",       positionR , yScale + (int)(yBaseOffset*5.2),screenScale * 0.9f , false);
        renderTextWithScale ( context ,  playerStats.getVitality ()+"" ,  attributePositionWidth, yScale + yBaseOffset*2,screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getEndurance ()+"" , attributePositionWidth, yScale + (int)(yBaseOffset*2.8),screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getStrength()+"",    attributePositionWidth, yScale + (int)(yBaseOffset*3.6),screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getAgility()+"",     attributePositionWidth, yScale + (int)(yBaseOffset*4.4),screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getLuck()+"",        attributePositionWidth, yScale + (int)(yBaseOffset*5.2),screenScale * 0.9f , true);

        renderTextWithScale ( context ,  "Показатели:" ,xScale + (width/12), yScale + (int)(yBaseOffset * 7.4), screenScale * 1.2f, false);
        renderTextWithScale ( context ,  "Здоровье",    xScale + (width/20), yScale + (int)(yBaseOffset*8.5),   0.9f, false);
        renderTextWithScale ( context ,  "Сила",        xScale + (width/20), yScale + (int)(yBaseOffset*9.3),   0.9f, false);
        renderTextWithScale ( context ,  "Защита",      xScale + (width/20), yScale + (int)(yBaseOffset*10.1),  0.9f, false);
        renderTextWithScale ( context ,  String.format ("%.2f", playerStats.getHealth()), xScale + (width/4), yScale + (int)(yBaseOffset*8.5), screenScale * 0.9f , true);
        renderTextWithScale ( context ,  String.format ("%.2f", playerStats.getAttack()), xScale + (width/4), yScale + (int)(yBaseOffset*9.3), screenScale * 0.9f , true);
        renderTextWithScale ( context ,  String.format ("%.2f", playerStats.getDefense()),xScale + (width/4), yScale + (int)(yBaseOffset*10.1), screenScale * 0.9f , true);

        renderTextWithScale ( context ,  "Защита:" ,                positionR , yScale + (int)(yBaseOffset * 7.4),screenScale * 1.2f, false);
        renderTextWithScale ( context ,  "Физический урон", positionR , yScale + (int)(yBaseOffset*8.5),  screenScale * 0.9f, false);
        renderTextWithScale ( context ,  "Отравление",      positionR , yScale + (int)(yBaseOffset*9.3),  screenScale * 0.9f, false);
        renderTextWithScale ( context ,  "Горение",         positionR , yScale + (int)(yBaseOffset*10.1), screenScale * 0.9f, false);
        renderTextWithScale ( context ,  "Иссушение",       positionR , yScale + (int)(yBaseOffset*10.9), screenScale * 0.9f, false);
        renderTextWithScale ( context ,  "Больше опыта",        positionR , yScale + (int)(yBaseOffset*11.7), screenScale * 0.9f, false);
        renderTextWithScale ( context ,  playerStats.getVitality() + "%",  attributePositionWidth, yScale + (int)(yBaseOffset*8.5), screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getEndurance()+ "%",  attributePositionWidth, yScale + (int)(yBaseOffset*9.3), screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getStrength()+ "%",   attributePositionWidth, yScale + (int)(yBaseOffset*10.1),screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getAgility()+ "%",    attributePositionWidth, yScale + (int)(yBaseOffset*10.9),screenScale * 0.9f , true);
        renderTextWithScale ( context ,  playerStats.getLuck()*5+ "%",       attributePositionWidth, yScale + (int)(yBaseOffset*11.7),screenScale * 0.9f , true);

        int levelTextPosition = (int)(width / 7.8f);
        renderTextWithScale ( context ,  "" + playerStats.getCurrentLevel(), levelTextPosition, yScale + (int)(yBaseOffset*12.15),screenScale * 0.9f , true);
    }

    private void renderCustomButtons(){
        int xScale = (int)(width/3.76f);
        int xBaseOffset = (int)(width/6f);
        int yScale = (int)(height/7.2f);
        int yBaseOffset = (int)(height/18.5f);
        float screenScale = getScaleForScreen(width, height);
        int positionR = xScale + xBaseOffset * 2 + (width/10);
        int buttonPositionWidth = (int)(positionR + width / 6 * screenScale);

        // Добавляем кнопки для изменения характеристик
        // Увеличиваем живучесть
        ButtonWidget vitalityButton = addDrawableChild ( ButtonWidget.builder ( Text.of ( "+" ) , button -> {
            playerStats.increaseVitality ( 1 ); // Увеличиваем живучесть
            refreshScreen ();
        } ).dimensions ( buttonPositionWidth , yScale + yBaseOffset*2-2 , 9 , 9 ).build () );

        // Увеличиваем Выносливость
        ButtonWidget enduranceButton = addDrawableChild ( ButtonWidget.builder ( Text.of ( "+" ) , button -> {
            playerStats.increaseEndurance ( 1 ); // Увеличиваем живучесть
            refreshScreen ();
        } ).dimensions ( buttonPositionWidth , yScale + (int)(yBaseOffset*2.8)-2 , 9 , 9 ).build () );

        // Увеличиваем силу
        ButtonWidget strengthButton = addDrawableChild ( ButtonWidget.builder ( Text.of ( "+" ) , button -> {
            playerStats.increaseStrength ( 1 ); // Увеличиваем силу
            refreshScreen ();
        } ).dimensions ( buttonPositionWidth , yScale + (int)(yBaseOffset*3.6)-2 , 9 , 9 ).build () );

        // Увеличиваем Ловкость
        ButtonWidget agilityButton = addDrawableChild ( ButtonWidget.builder ( Text.of ( "+" ) , button -> {
            playerStats.increaseAgility ( 1 ); // Увеличиваем силу
            refreshScreen ();
        } ).dimensions ( buttonPositionWidth , yScale + (int)(yBaseOffset*4.4)-2 , 9 , 9 ).build () );

        // Увеличиваем удача
        ButtonWidget luckButton = addDrawableChild ( ButtonWidget.builder ( Text.of ( "+" ) , button -> {
            playerStats.increaseLuck ( 1 ); // Увеличиваем силу
            refreshScreen ();
        } ).dimensions ( buttonPositionWidth , yScale + (int)(yBaseOffset*5.2)-2 , 9 , 9 ).build () );

    }

    // Метод для отрисовки текста с заданным масштабом
    private void renderTextWithScale(DrawContext context, String text, int x, int y, float scale, boolean center) {
        // Вычисляем ширину текста без учета масштаба
        int textWidth = textRenderer.getWidth(Text.literal(text));

        textWidth *= scale;  // Увеличиваем ширину с учетом масштаба

        // Сохраняем текущую матрицу
        context.getMatrices().push();

        // Применяем масштаб
        context.getMatrices().scale(scale, scale, 1.0f);

        // Если включено центрирование
        if (center) {
            // Центрируем текст относительно x с учетом масштаба
            x -= textWidth / 2;
        }

        // Рисуем текст с центровкой и тенью, делим координаты на scale, чтобы вернуть их в нормальный размер
        context.drawTextWithShadow(textRenderer, Text.literal(text), (int)(x / scale), (int)(y / scale), 0xfff3e0);

        // Восстанавливаем матрицу после отрисовки
        context.getMatrices().pop();
    }

    private float getScaleForScreen(int width, int height) {
        // Пример масштабирования в зависимости от размера экрана
        float baseWidth = 640f; // Базовая ширина экрана, например, 1920px
        float baseHeight = 360f; // Базовая высота экрана, например, 1080px

        // Масштаб по ширине
        float scaleX = width / baseWidth;
        // Масштаб по высоте
        float scaleY = height / baseHeight;

        // Выбираем минимальный масштаб, чтобы текст не был слишком большим
        return Math.min(scaleX, scaleY);
    }

    private void renderBackground(DrawContext context, int baseWidth, int baseHeight, int x, int y, Identifier backgroundTexture) {
        // Масштабирование размеров текстуры относительно экрана
        float widthScaleFactor = width / 640f; // Коэффициент для масштабирования ширины
        float heightScaleFactor = height / 360f; // Коэффициент для масштабирования высоты

        // Применяем масштаб к ширине и высоте фона
        int scaledWidth = (int) (baseWidth * widthScaleFactor);
        int scaledHeight = (int) (baseHeight * heightScaleFactor);

        // Отрисовка фона
        context.drawTexture(backgroundTexture, x, y, 0, 0, scaledWidth, scaledHeight, scaledWidth, scaledHeight);
    }

    public static void renderPlayerModelWithDrawContext(DrawContext context, int x, int y, int size, float mouseX, float mouseY, PlayerEntity player) {
        // Вычисляем разницу координат между курсором и моделью
        double deltaX = mouseX - x;  // Разница по X (горизонталь)
        double deltaY = mouseY - y / 2;  // Разница по Y (вертикаль)

        context.getMatrices ().push ();
        context.getMatrices ().translate ( 0,0,105 );
        // Отрисовываем игрока с учетом углов по осям X и Y
        InventoryScreen.drawEntity(context, x, y, size, (float) -deltaX , (float) -deltaY , player);
        context.getMatrices ().pop ();
    }

    @Override
    public void onDisplayed() {
        super.onDisplayed();
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return true;  // Close the screen on pressing ESC
    }
    private void refreshScreen() {
        // Обновляем отображение экрана (если требуется)
        clearChildren();
        init();
    }
}
