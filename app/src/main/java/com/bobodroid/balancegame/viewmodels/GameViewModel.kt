package com.bobodroid.balancegame.viewmodels

import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.toMutableStateList
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
        MutableStateFlow<GameItem>(GameItem(0, "", "00", "00", ItemKind.FOOD))

    val currentRandomGameItem: StateFlow<GameItem> = _currentRandomGameItem.asStateFlow()

    private val _usedGameItem = MutableStateFlow<List<UsedGameItem>>(emptyList())

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


    val item1 = GameItem(1, "보보", "짬뽕", "짜장", ItemKind.FOOD)
    val item2 = GameItem(2, "보보", "우동", "라면", ItemKind.FOOD)
    val item3 = GameItem(3, "보보", "딸기", "포도", ItemKind.FOOD)
    val item4 = GameItem(4, "보보", "수박", "레몬", ItemKind.FOOD)
    val item5 = GameItem(5, "보보", "찍먹", "부먹", ItemKind.FOOD)
    val item6 = GameItem(6, "보보", "초밥", "회", ItemKind.FOOD)
    val item7 = GameItem(7, "보보", "고기", "생선", ItemKind.FOOD)
    val item8 = GameItem(8, "보보", "콜라", "사이다", ItemKind.FOOD)
    val item9 = GameItem(9, "보보", "고구마", "감자", ItemKind.FOOD)
    val item10 = GameItem(10, "라민", "한우", "삼겹살", ItemKind.FOOD)
    val item11 = GameItem(11, "라민", "우주", "바다", ItemKind.TRAVEL)
    val item12 = GameItem(12, "라민", "강", "산", ItemKind.TRAVEL)
    val item13 = GameItem(13, "라민", "엄마", "아빠", ItemKind.ETC)
    val item14 = GameItem(14, "라민", "딸", "아들", ItemKind.ETC)
    val item15 = GameItem(15, "라민", "키스", "포옹", ItemKind.LOVE)


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

//    fun startTogetherGame() {
//        viewModelScope.launch {
//            val togetherGame = togetherGame(_gameItemFlow.value, codeMatchingGameItem)
//        }
//    }


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


//    fun togetherGame(gameItemList: List<GameItem>, saveItemList: Flow<List<UserSaveGame>>): List<GameItem> {
//        val tempGameItemList = gameItemList
//
//        val saveItem = saveItemList.stateIn(CoroutineScope())
//
//        val saveItemId = saveItem.map {  }
//
//        val finalGameItemList = tempGameItemList.filter { it.id == saveItemId }
//
//        return finalGameItemList
//    }








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

    fun usedGameItem() {
        viewModelScope.launch {
            val usedGameItem = _usedGameItem.value
            val items = usedGameItem.toMutableList().apply {
                add(
                    UsedGameItem(
                        currentRandomGameItem.value.id!!,
                        stageResultValue.value
                    )
                )
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




    val success =
        userSaveGameItem.combine(gameCode.filterNot { it.isEmpty() }) { saveList, inputGameCode ->
            saveList.map { it.GameCode.toString() == inputGameCode }

        }

}




data class UsedGameItem(val gameId: Int, val resultGameValue: Int)

data class UserSaveGame(val listNumber: Int, val GameCode: UUID = randomUUID(), val saveName: String, val gameItems: List<UsedGameItem>)







