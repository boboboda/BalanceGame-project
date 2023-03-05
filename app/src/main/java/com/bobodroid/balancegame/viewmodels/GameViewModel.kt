package com.bobodroid.balancegame.viewmodels

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.viewmodels.dataViewModels.GameItem
import com.bobodroid.balancegame.viewmodels.dataViewModels.ItemKind
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.UUID.randomUUID

class GameViewModel: ViewModel() {

    private val _gameItemFlow = MutableStateFlow<List<GameItem>>(emptyList())

    val gameItemFlow = _gameItemFlow.asStateFlow()

    private val _kindCurrentGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    val kindCurrentGameItem = _kindCurrentGameItem.asStateFlow()

    private val _currentRandomGameItem =
        MutableStateFlow<GameItem>(GameItem(0, "", "00", "00", null ,ItemKind.FOOD))

    val currentRandomGameItem: StateFlow<GameItem> = _currentRandomGameItem.asStateFlow()

    private val _usedGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    val usedGameItem = _usedGameItem.asStateFlow()

    val singleGameState = MutableStateFlow(false)

    val singleGameStage = MutableStateFlow(1)

    val togetherGameState = MutableStateFlow(false)

    val togetherGameStage = MutableStateFlow(1)

    val selectedKindItem = MutableStateFlow(ItemKind.FOOD)

    val isPlayGame = MutableStateFlow(true)

    val stageResultValue = MutableStateFlow(0)

    val saveName = MutableStateFlow("")

    private val _userSaveGameItem = MutableStateFlow<List<UserSaveGame>>(emptyList())

    val userSaveGameItem = _userSaveGameItem.asStateFlow()

    val listNumber = MutableStateFlow(0)

    val gameCode = MutableStateFlow("")


    val item1 = GameItem(1, "보보", "짬뽕", "짜장",null, ItemKind.FOOD)
    val item2 = GameItem(2, "보보", "우동", "라면",null, ItemKind.FOOD)
    val item3 = GameItem(3, "보보", "딸기", "포도",null, ItemKind.FOOD)
    val item4 = GameItem(4, "보보", "수박", "레몬",null, ItemKind.FOOD)
    val item5 = GameItem(5, "보보", "찍먹", "부먹",null, ItemKind.FOOD)
    val item6 = GameItem(6, "보보", "초밥", "회",null, ItemKind.FOOD)
    val item7 = GameItem(7, "보보", "고기", "생선",null, ItemKind.FOOD)
    val item8 = GameItem(8, "보보", "콜라", "사이다",null, ItemKind.FOOD)
    val item9 = GameItem(9, "보보", "고구마", "감자",null, ItemKind.FOOD)
    val item10 = GameItem(10, "라민", "한우", "삼겹살",null, ItemKind.FOOD)
    val item11 = GameItem(11, "라민", "우주", "바다",null, ItemKind.TRAVEL)
    val item12 = GameItem(12, "라민", "강", "산",null, ItemKind.TRAVEL)
    val item13 = GameItem(13, "라민", "엄마", "아빠",null, ItemKind.ETC)
    val item14 = GameItem(14, "라민", "딸", "아들",null, ItemKind.ETC)
    val item15 = GameItem(15, "라민", "키스", "포옹",null, ItemKind.LOVE)


    val items = listOf(
        item1,
        item2,
        item3,
        item4,
        item5,
        item6,
        item7,
        item8,
        item9,
        item10,
        item11,
        item12,
        item13,
        item14,
        item15
    )

    val codeMatchingGameItem =
        userSaveGameItem.combine(gameCode.filterNot { it.isEmpty() }) { saveList, inputGameCode ->
            saveList.filter { it.GameCode.toString() == inputGameCode }
        }

    val _startSaveGameValue = MutableStateFlow<UserSaveGame>(UserSaveGame(0, randomUUID(),"", emptyList()))

    val startSaveGameValue = _startSaveGameValue.replayCache

    val matchingGameItemState: StateFlow<List<UserSaveGame>> = codeMatchingGameItem.stateIn(viewModelScope, SharingStarted.Eagerly, startSaveGameValue)


    private val _togetherGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    val togetherGameItem = _togetherGameItem.asStateFlow()

    val _currentTogetherGameItem =  MutableStateFlow<GameItem>(GameItem(0, "", "00", "00", null, ItemKind.FOOD))

    val currentTogetherGameItem = _currentTogetherGameItem.asStateFlow()


    val success = userSaveGameItem.filterNot { it.isEmpty() }.combine(gameCode.filterNot { it.isEmpty() })  { saveList, inputGameCode ->
        saveList.map { it.GameCode.toString() == inputGameCode }.last() }


    init {

        _gameItemFlow.value = items

        viewModelScope.launch {
            singleGameState.collectLatest {
                selectedKindItem
                    .collectLatest {
                        val kindFilterItem = getKindFilterItem(_gameItemFlow.value)
                        _kindCurrentGameItem.emit(kindFilterItem)
                        singleGameStage
                            .collectLatest {
                                val (shirnkedList, randomGameItem) = getRandomGameItem(
                                    _kindCurrentGameItem.value
                                )
                                _kindCurrentGameItem.emit(shirnkedList)
                                _currentRandomGameItem.emit(randomGameItem)
                            }
                    }

            }

        }

    }
    fun startTogetherGame(){
        viewModelScope.launch {
                val (shirnkedList, togetherRandomGameItem) = startTogetherGame(matchingGameItemState)
                _togetherGameItem.emit(shirnkedList)
                _currentTogetherGameItem.emit(togetherRandomGameItem)

        }
    }

    fun getKindFilterItem(gameItemList: List<GameItem>): List<GameItem> {
        var tempGameItemList = gameItemList
        val kindGameItem = tempGameItemList.filter { it.itemKind == selectedKindItem.value }
        return kindGameItem
    }

    fun getRandomGameItem(gameItemList: List<GameItem>): Pair<List<GameItem>, GameItem> {

        var tempGameItemList = gameItemList

        val randomGameItem = gameItemList.random()

        val finalGameItemList = tempGameItemList.filter { it.id != randomGameItem.id }

        return Pair(finalGameItemList, randomGameItem)
    }


    fun startTogetherGame(saveItemList: StateFlow<List<UserSaveGame>>): Pair<List<GameItem>, GameItem> {

        val tempGameItemList = saveItemList.value.flatMap { it.gameItems }

        val saveItemRandom = saveItemList.value.flatMap { it.gameItems }.random()

        val finalGameItemList = tempGameItemList.filter { it.id != saveItemRandom.id }

        return Pair(finalGameItemList, saveItemRandom)
    }


    fun gameItemReset() {
        viewModelScope.launch {
            singleGameState.value = true
            singleGameStage.emit(1)
            saveName.emit("")
        }
    }

    fun moveToNextStage() {
        viewModelScope.launch {
            if (singleGameStage.value != 10)
                singleGameStage.emit(singleGameStage.value.plus(1))
        }
    }

    fun togetherMoveToNextStage() {
        viewModelScope.launch {
            if (togetherGameStage.value != 10)
                togetherGameStage.emit(togetherGameStage.value.plus(1))
        }
    }

    fun togetherGameItemReset() {
        viewModelScope.launch {
            togetherGameState.value = true
            togetherGameStage.emit(1)
        }
    }


    fun usedGameItem(gameItem: GameItem) {
        viewModelScope.launch {
            val usedGameItem = _usedGameItem.value

            val items = usedGameItem.toMutableList().apply {
                add(GameItem(gameItem.id, gameItem.makerName, gameItem.firstItem, gameItem.secondItem,  stageResultValue.value , gameItem.itemKind))
            }.toList()
            _usedGameItem.value = items

        }
    }



    fun saveGameItem() {
        viewModelScope.launch {

            val userSaveItem = _userSaveGameItem.value
            val items = userSaveItem.toMutableList().apply {
                add(
                    UserSaveGame(
                        listNumber = listNumber.value,
                        randomUUID(),
                        saveName.value,
                        usedGameItem.value
                    )
                )
            }
            _userSaveGameItem.value = items
        }
    }


   private val _compatibilityValue = MutableStateFlow<List<MatchSelect>>(emptyList())

    val compatibilityFlow = _compatibilityValue.asStateFlow()

    val compatibilityListNumber = MutableStateFlow(0)

    val firstUserSelect = MutableStateFlow(0)

    val secondUserSelect = MutableStateFlow(0)

    val compatibilityPercent = MutableStateFlow("")


    fun actionSelect() {
        viewModelScope.launch {

            val firstItem = _currentTogetherGameItem.value.firstItem

            val secondItem = _currentTogetherGameItem.value.secondItem

            val firstUser = if(firstUserSelect.value == 1) firstItem else secondItem

            val secondUser = if(secondUserSelect.value == 1) firstItem else secondItem

            val compatibilityList = _compatibilityValue.value

            val compatibilityResult = if(firstUserSelect.value == secondUserSelect.value) true else false

            val items = compatibilityList.toMutableList().apply {
                add(MatchSelect(compatibilityListNumber.value, firstUser!!, secondUser!!, compatibilityResult)) }

            _compatibilityValue.value = items

//            compatibilityPercent.emit(finalCompatibility().plus(finalCompatibility()).toString())

            }
        }



    private fun finalCompatibility() = (if(firstUserSelect.value == secondUserSelect.value) 0 else 1) * 10



}







data class MatchSelect(val listNumber: Int, val firstUserSelect: String, val SecondUserSelect: String, val selectResult: Boolean)

data class UsedGameItem(val gameId: Int = 0,  val resultGameValue: Int)

data class UserSaveGame(val listNumber: Int, val GameCode: UUID = randomUUID(), val saveName: String, val gameItems: List<GameItem>)







